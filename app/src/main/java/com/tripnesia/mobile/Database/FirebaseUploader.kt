package com.tripnesia.mobile.Database

import com.google.firebase.database.FirebaseDatabase
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.data.dummy.TravelPackageData
fun uploadDataToFirebase() {
    val database = FirebaseDatabase.getInstance()
    val destinationRef = database.getReference("destinations")
    val packageRef = database.getReference("travelPackage")
    val eventRef = database.getReference("events")

    DestinationData.destinations.forEach { destination ->
        val cleanedData = Destination(
            id = destination.id,
            title = destination.title,
            description = destination.description,
            imageUrl = destination.imageUrl,
            placeQuery = destination.placeQuery,
            location = destination.location,
            rating = destination.rating,
            price = destination.price,
            openingHours = destination.openingHours
        )
        destinationRef.child(destination.id.toString()).setValue(cleanedData)
    }

    TravelPackageData.travelPackage.forEach { travelPackage ->
        val cleanedData = TravelPackage(
            id = travelPackage.id,
            name = travelPackage.name,
            description = travelPackage.description,
            imageUrl = travelPackage.imageUrl,
            location = travelPackage.location,
            durationDays = travelPackage.durationDays,
            facilities = travelPackage.facilities,
            price = travelPackage.price,
            isAvailable = travelPackage.isAvailable,
            rating = travelPackage.rating,
            createdAt = travelPackage.createdAt
        )

        packageRef.child(travelPackage.id).setValue(cleanedData)
    }

//    sampleEvents.forEach { event ->
//        val cleanedEvent = Event(
//            id = event.id,
//            title = event.title,
//            date = event.date,
//            kategori = event.kategori,
//            imageName = "pesta_bali", // Sesuaikan
//            description = event.description
//        )
//        eventRef.child(event.id.toString()).setValue(cleanedEvent)
//    }
}
