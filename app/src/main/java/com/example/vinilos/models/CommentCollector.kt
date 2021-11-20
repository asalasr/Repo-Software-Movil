package com.example.vinilos.models

data class CommentCollector (
    val rating:Int,
    val description:String,
    val collector : CollectorComment
)


data class CollectorComment (
    val id : Int
)