package com.tripnesia.mobile.data.model
import java.io.Serializable

data class Destination(
    val id: Int,
    val title: String,
    val description: String,
    val imageRes: Int
): Serializable