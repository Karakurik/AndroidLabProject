package com.itis.androidlabproject.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapter.TaskAdapter
import com.itis.androidlabproject.data.AppDatabase
import com.itis.androidlabproject.databinding.FragmentTaskListBinding
import com.itis.androidlabproject.item_decorator.SpaceItemDecorator
import com.itis.androidlabproject.models.Task

class TaskListFragment : Fragment(R.layout.fragment_task_list) {
    private var binding: FragmentTaskListBinding? = null
    private var database: AppDatabase? = null
    private var taskAdapter: TaskAdapter? = null
    private var tasks: List<Task>? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskListBinding.bind(view)
        database = AppDatabase.invoke(context) as AppDatabase
        taskAdapter = TaskAdapter({showTaskFragment(it)}, {deleteTask(it)})

        binding?.apply {
            toolBar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
            fabAdd.setOnClickListener {
                showTaskFragment(null)
            }
            rvTasks.run {
                adapter = taskAdapter
                addItemDecoration(
                    DividerItemDecoration(context, RecyclerView.VERTICAL)
                )
                addItemDecoration(
                    SpaceItemDecorator(context)
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.icon_delete_all -> {
                deleteAllTasks()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllTasks() {
        database?.taskDao()?.deleteAllTasks()
        updateTasks()
        showMessage("Задачи удалены")
    }

    private fun deleteTask(id: Int) {
        database?.taskDao()?.deleteTaskById(id)
        updateTasks()
        showMessage("Задача удалена")
    }

    private fun updateTasks() {
        tasks = database?.taskDao()?.getAll()
        binding?.apply {
            if (tasks.isNullOrEmpty()) {
                rvTasks.visibility = View.GONE
                noTasksAdded.visibility = View.VISIBLE
            } else {
                rvTasks.visibility = View.VISIBLE
                noTasksAdded.visibility = View.GONE
            }
        }
        taskAdapter?.submitList(ArrayList(tasks))
    }

    private fun showTaskFragment(id: Int?) {
        var bundle: Bundle? = null
        id?.also {
            bundle = Bundle().apply {
                putInt("ARG_TASK_ID", id)
            }
        }
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

        findNavController().navigate(
            R.id.action_taskListFragment_to_taskDetailsFragment,
            bundle,
            options
        )
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        database = null
        taskAdapter = null
    }
}
