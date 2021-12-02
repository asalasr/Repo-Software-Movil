package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val createDate: String,
    val albumsId: Array<Int>,
    val prizesId: Array<Int>
): Parcelable