package com.example.demo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantData(
    val name: String,
    val description: String,
    val pictureURL: String
): Parcelable