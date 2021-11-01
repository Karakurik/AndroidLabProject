package com.itis.androidlabproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidlabproject.databinding.ActivityMainBinding
import com.itis.androidlabproject.fragments.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, ListFragment())
            addToBackStack(null)
            commit()
        }
    }
}
