package com.itis.androidlabproject.repositories

import com.itis.androidlabproject.models.Planet

object PlanetRepository {
    val planets = arrayListOf<Planet>(
        Planet(1,"Земля", 434334, "//Glide\n" +
                "    implementation 'com.github.bumptech.glide:glide:4.12.0'\n" +
                "    implementation \"com.squareup.okhttp3:okhttp:5.0.0-alpha.2\""),
        Planet(2,"Венера", 27455),
        Planet(3,"Меркурий", 623532)
    )

    fun getRabbitById(id: Int): Planet {
        return this.planets[id-1]
    }
}
