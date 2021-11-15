package com.itis.androidlabproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.itis.androidlabproject.R
import com.itis.androidlabproject.databinding.ActivityMainBinding
import com.itis.androidlabproject.extension.findNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        navController = findNavController(R.id.fragment_container)
    }
}
