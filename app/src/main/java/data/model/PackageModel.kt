package com.tripnesia.mobile.data.model
import java.io.Serializable

data class TravelPackage(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val location: String = "",
    val durationDays: Int = 0,
    val facilities: List<String> = emptyList(),
    val price: Int = 0,
    val isAvailable: Boolean = true,
    val rating: Float = 0f,
    val createdAt: Long = 0L
) : Serializable