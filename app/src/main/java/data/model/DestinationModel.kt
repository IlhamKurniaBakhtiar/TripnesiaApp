package com.tripnesia.mobile.data.model
import java.io.Serializable

data class Destination(
    val id: Int,
    val title: String,
    val description: String,
    val imageRes: Int,
    val placeQuery: String,
    val location: String,
    val rating: Double,
    val price: String,
    val openingHours: String
): Serializable