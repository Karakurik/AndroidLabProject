package com.itis.androidlabproject.models

data class Planet(
    var id: Int,
    var name: String,
    var age: Int,
    var imageUrl: String = "",
    var description: String = ""
) {

}
