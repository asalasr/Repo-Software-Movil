package com.example.vinilos.models

import java.util.*

data class Album(
    val name:String,
    val cover:String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val id:Int?
)
