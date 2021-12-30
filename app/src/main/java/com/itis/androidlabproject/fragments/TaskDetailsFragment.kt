package com.itis.androidlabproject.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.androidlabproject.R
import com.itis.androidlabproject.data.AppDatabase
import com.itis.androidlabproject.databinding.FragmentDetailsTaskBinding
import com.itis.androidlabproject.fragments.date_picker.DatePickerFragment
import com.itis.androidlabproject.models.DateToStringConverter
import com.itis.androidlabproject.models.Task
import com.itis.androidlabproject.view.MainActivity
import kotlinx.coroutines.*
import java.util.*

class TaskDetailsFragment : Fragment(R.layout.fragment_details_task) {
    private var binding: FragmentDetailsTaskBinding? = null
    private var database: AppDatabase? = null
    private var client: FusedLocationProviderClient? = null
    private var calendar: Calendar? = null
    private var currentTaskId: Int? = null

    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = LocationServices.getFusedLocationProviderClient(activity)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentDetailsTaskBinding.bind(view)
        database = AppDatabase.invoke(context) as AppDatabase

        binding?.apply {
            toolBar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener {
                    (activity as? MainActivity)?.onBackPressed()
                }
                setOnMenuItemClickListener { onOptionsItemSelected(it) }
            }

            btnChooseDate.setOnClickListener {
                showDatePicker()
            }
        }

        checkIfTaskExists()
        setLocation()
    }

    private fun setLocation() {
        if (checkPermissions() == true) {
            getCurrentLocation()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_CODE
            )
        }
    }

    private fun checkPermissions(): Boolean? {
        activity?.apply {
            return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED)
        }
        return null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            returnToTaskListFragment()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) or
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            if (checkPermissions() == true) {
                client?.lastLocation?.addOnCompleteListener {
                    val location = it.result
                    if (location != null) {
                        binding?.etLongitude?.setText(location.longitude.toString())
                        binding?.etLatitude?.setText(location.latitude.toString())
                    }
                }
            }
        } else {
            startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.icon_save -> {
                saveTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkIfTaskExists() {
        arguments?.getInt("ARG_TASK_ID")?.let {
            currentTaskId = it
            scope.launch {
                setTaskEditingView(it)
            }
        }
    }

    private suspend fun setTaskEditingView(id: Int) {
        val task = scope.async {
            withContext(Dispatchers.IO) {
                database?.taskDao()?.findById(id)
            }
        }.await()
        binding?.apply {
            etTitle.setText(task?.title)
            etDesc.setText(task?.description)
            task?.date?.let {
                calendar = Calendar.getInstance()
                calendar?.time = it
                tvDate.text = DateToStringConverter.convertDateToString(it)
                tvDate.visibility = View.VISIBLE
            }
        }
    }

    private fun showDatePicker() {
        calendar = Calendar.getInstance()
        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                calendar?.timeInMillis = bundle.getLong("SELECTED_DATE")
                setDate(calendar)
            }
        }
        datePickerFragment.show(supportFragmentManager, "DatePickerDialog")
    }

    private fun setDate(calendar: Calendar?) {
        binding?.apply {
            tvDate.text = DateToStringConverter.convertDateToString(calendar?.time)
            tvDate.visibility = View.VISIBLE
        }
    }

    private fun saveTask() {
        if (currentTaskId == null && isTaskCorrect()) {
            addTask()
        } else {
            currentTaskId?.let {
                scope.launch {
                    updateTask(it)
                }
            }
        }
        returnToTaskListFragment()
    }

    private fun addTask() {
        binding?.apply {
            scope.launch {
                withContext(Dispatchers.IO) {
                    database?.taskDao()?.insert(
                        Task(
                            null,
                            etTitle.text.toString(),
                            etDesc.text.toString(),
                            calendar?.time,
                            etLongitude.text as? Double,
                            etLatitude.text as? Double
                        )
                    )
                }
            }
        }
        showMessage("Задача сохранена")
        returnToTaskListFragment()
    }

    private suspend fun updateTask(id: Int) {
        val task = scope.async {
            withContext(Dispatchers.IO) {
                database?.taskDao()?.findById(id)
            }
        }.await()
        binding?.apply {
            if (isTaskCorrect()) {
                binding?.run {
                    task?.let { task ->
                        task.title = etTitle.text.toString()
                        task.description = etDesc.text.toString()
                        calendar?.also {
                            task.date = it.time
                        }
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                database?.taskDao()?.update(task)
                            }
                        }
                        showMessage("Задача обновлена")
                        returnToTaskListFragment()
                    }
                }
            }
        }
        returnToTaskListFragment()
    }

    private fun isTaskCorrect(): Boolean {
        binding?.run {
            if (etTitle.text.toString().isEmpty()) {
                showMessage("Нет названия")
                return false
            }
            if (etDesc.text.toString().isEmpty()) {
                showMessage("Нет описания")
                return false
            }
        }
        return true
    }


    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun returnToTaskListFragment() {
        (activity as? MainActivity)?.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        binding = null
        database = null
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}
