package com.tripnesia.mobile.data.model
import java.io.Serializable

data class Event(
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    val kategori: String = "",
    val imageName: String = "",
    val description: String = ""
): Serializable
