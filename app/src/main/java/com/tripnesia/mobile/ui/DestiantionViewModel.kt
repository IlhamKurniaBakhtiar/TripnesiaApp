package com.tripnesia.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DestinationViewModel : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    val destinations: StateFlow<List<Destination>> = _destinations

    private val databaseRef = FirebaseDatabase.getInstance().getReference("destinations")

    init {
        fetchDestinationsFromFirebase()
    }

    private fun fetchDestinationsFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val destinationList = mutableListOf<Destination>()
                for (childSnapshot in snapshot.children) {
                    val destination = childSnapshot.getValue(Destination::class.java)
                    destination?.let { destinationList.add(it) }
                }
                _destinations.value = destinationList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getDestinationById(id: Int): Destination? {
        return _destinations.value.find { it.id == id }
    }
}