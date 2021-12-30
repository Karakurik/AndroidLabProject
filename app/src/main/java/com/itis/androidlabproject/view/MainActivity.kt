package com.itis.androidlabproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.itis.androidlabproject.R
import com.itis.androidlabproject.databinding.ActivityMainBinding
import com.itis.androidlabproject.extensions.findController

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        navController = findController(R.id.fragment_container)

//        binding?.fragmentContainer?.findNavController()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        navController = null
    }
}
