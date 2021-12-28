package com.itis.androidlabproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.itis.androidlabproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var controller: NavController? = null
    private var binding: ActivityMainBinding? = null

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
