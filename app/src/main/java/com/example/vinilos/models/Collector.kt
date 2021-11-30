package com.example.vinilos.models

import java.io.Serializable

data class Collector (
 val name: String,
    val email: String,
    val telephone: String,
    val albumsId: Array<Int>
): Serializable