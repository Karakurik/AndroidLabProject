package com.itis.androidlabproject.model

data class Planet(
    var id: Int,
    var name: String,
    val numberOfSatellite: Int = 0,
    var url: String,
    var description: String
)
