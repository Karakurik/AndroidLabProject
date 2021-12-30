package com.itis.androidlabproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.itis.androidlabproject.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {
    private var controller: NavController? = null
    private var binding: ActivityMainBinding? = null

//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(binding?.root)
        }
        controller = binding?.container?.id?.let { findNavController(it) }
    }

    override fun onBackPressed() {
        supportFragmentManager.run {
            if (backStackEntryCount == 0) {
                super.onBackPressed()
            } else {
                popBackStack()
            }
        }
    }
}
