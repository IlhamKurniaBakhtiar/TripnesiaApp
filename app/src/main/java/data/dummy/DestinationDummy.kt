

package com.tripnesia.mobile.data.dummy

import com.tripnesia.mobile.data.model.Destination

object DestinationData {
    val destinations = listOf(
        Destination(
            id = 1,
            title = "Raja Ampat",
            description = "Surga bawah laut yang indah di Papua.",
            imageRes = com.tripnesia.mobile.R.drawable.raja_ampat
        ),
        Destination(
            id = 2,
            title = "Pantai Kuta",
            description = "Pantai terkenal di Bali dengan pasir putih.",
            imageRes = com.tripnesia.mobile.R.drawable.kuta
        ),
        Destination(
            id = 3,
            title = "Labuan Bajo",
            description = "Gerbang ke Taman Nasional Komodo.",
            imageRes = com.tripnesia.mobile.R.drawable.labuan_bajo
        ),
        Destination(
            id = 4,
            title = "Pulau Komodo",
            description = "Habitat asli Komodo, warisan dunia.",
            imageRes = com.tripnesia.mobile.R.drawable.pulau_komodo
        )
    )
}
