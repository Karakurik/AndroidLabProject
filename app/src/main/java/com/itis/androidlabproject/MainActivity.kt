package com.itis.androidlabproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var simpleCalculator = Calculator("5.2+4")
        var gCalculator = GraphingCalculator("y=5*x")
        var engineeringCalculator = EngineeringCalculator("")

        println(simpleCalculator.calculate())
    }
}
