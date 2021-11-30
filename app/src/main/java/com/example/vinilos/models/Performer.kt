package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val createDate: String
): Parcelable