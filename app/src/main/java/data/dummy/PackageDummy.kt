package com.tripnesia.mobile.data.dummy

import com.tripnesia.mobile.R
import com.tripnesia.mobile.data.model.TravelPackage

object TravelPackageData {
    val travelPackage = listOf(
        TravelPackage(
            id = "pkg001",
            name = "Camping di Ranu Kumbolo",
            description = "Camping seru di tepi danau gunung Semeru, sunrise yang menawan.",
            imageUrl = "ranu_kumbolo",
            location = "Lumajang, Jawa Timur",
            durationDays = 3,
            facilities = listOf("Tenda", "Porter", "Makan", "Guide Gunung"),
            price = 950000,
            isAvailable = true,
            rating = 4.9f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg002",
            name = "Trekking Kawah Ijen & Blue Fire",
            description = "Trekking malam hari menuju fenomena api biru yang langka.",
            imageUrl = "kawah_ijen",
            location = "Banyuwangi",
            durationDays = 2,
            facilities = listOf("Homestay", "Transportasi", "Masker Gas", "Guide"),
            price = 700000,
            isAvailable = true,
            rating = 4.8f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg003",
            name = "Gunung Prau Sunrise Adventure",
            description = "Pendakian ringan untuk melihat sunrise terbaik di Jawa Tengah.",
            imageUrl = "prau",
            location = "Dieng, Wonosobo",
            durationDays = 2,
            facilities = listOf("Tenda", "Logistik", "Guide", "Makan"),
            price = 600000,
            isAvailable = true,
            rating = 4.7f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg004",
            name = "Explore Green Canyon & Body Rafting",
            description = "Petualangan seru di sungai dan gua dengan body rafting.",
            imageUrl = "green_canyon",
            location = "Pangandaran",
            durationDays = 2,
            facilities = listOf("Perahu", "Guide", "Pelampung", "Makan Siang"),
            price = 800000,
            isAvailable = true,
            rating = 4.6f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg005",
            name = "Snorkeling Trip Pulau Menjangan",
            description = "Keindahan bawah laut terbaik di Bali barat.",
            imageUrl = "menjangan",
            location = "Bali Barat",
            durationDays = 1,
            facilities = listOf("Alat Snorkeling", "Transportasi", "Makan Siang"),
            price = 750000,
            isAvailable = true,
            rating = 4.5f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg006",
            name = "Camping & Tubing Sungai Elo",
            description = "Main air dan camping seru di tepi sungai.",
            imageUrl = "sungai_elo",
            location = "Magelang",
            durationDays = 2,
            facilities = listOf("Tenda", "Alat Tubing", "Makan", "Instruktur"),
            price = 550000,
            isAvailable = true,
            rating = 4.4f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg007",
            name = "Trip Telaga Warna & Dieng Plateau",
            description = "Wisata dataran tinggi: telaga, kawah, dan budaya.",
            imageUrl = "telaga_warna",
            location = "Dieng, Wonosobo",
            durationDays = 2,
            facilities = listOf("Penginapan", "Transportasi", "Guide Lokal"),
            price = 680000,
            isAvailable = true,
            rating = 4.3f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg008",
            name = "Hiking Gunung Bromo via Sunrise Point",
            description = "Nikmati sunrise spektakuler di puncak Bromo.",
            imageUrl = "bromo",
            location = "Probolinggo",
            durationDays = 2,
            facilities = listOf("Jeep", "Guide", "Penginapan", "Tiket Masuk"),
            price = 900000,
            isAvailable = true,
            rating = 4.9f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg009",
            name = "Rafting Sungai Ayung + Eco Trekking",
            description = "Petualangan seru di alam Ubud, cocok untuk pemula.",
            imageUrl = "sungai_ayung",
            location = "Ubud, Bali",
            durationDays = 1,
            facilities = listOf("Rafting", "Guide", "Transportasi", "Snack"),
            price = 850000,
            isAvailable = true,
            rating = 4.6f,
            createdAt = System.currentTimeMillis()
        ),
        TravelPackage(
            id = "pkg010",
            name = "Camping dan Outbound Curug Cilember",
            description = "Family camp dengan aktivitas alam dan outbound seru.",
            imageUrl = "curug_cilember",
            location = "Puncak, Bogor",
            durationDays = 2,
            facilities = listOf("Tenda", "Outbound", "Makan", "Kamar Mandi"),
            price = 570000,
            isAvailable = true,
            rating = 4.2f,
            createdAt = System.currentTimeMillis()
        )
    )
}
