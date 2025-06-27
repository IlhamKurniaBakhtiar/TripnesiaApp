package com.tripnesia.mobile.data.model
import java.io.Serializable

data class Destination(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val placeQuery: String = "",
    val location: String = "",
    val rating: Double = 0.0,
    val price: String = "",
    val openingHours: String = ""
): Serializable