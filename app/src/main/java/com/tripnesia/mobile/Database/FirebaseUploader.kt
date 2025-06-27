package Database

import com.google.firebase.database.FirebaseDatabase
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination


fun uploadDataToFirebase() {
    val database = FirebaseDatabase.getInstance()
    val destinationRef = database.getReference("destinations")
    val eventRef = database.getReference("events")

    DestinationData.destinations.forEach { destination ->
        val cleanedData = Destination(
            id = destination.id,
            title = destination.title,
            description = destination.description,
            imageUrl = "raja_ampat",
            placeQuery = destination.placeQuery,
            location = destination.location,
            rating = destination.rating,
            price = destination.price,
            openingHours = destination.openingHours
        )
        destinationRef.child(destination.id.toString()).setValue(cleanedData)
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
