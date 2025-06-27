package com.tripnesia.mobile.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.tripnesia.mobile.data.model.TravelPackage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PackageViewModel : ViewModel() {

    private val _packages = MutableStateFlow<List<TravelPackage>>(emptyList())
    val packages: StateFlow<List<TravelPackage>> = _packages

    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("travelPackage")

    init {
        fetchPackagesFromFirebase()
    }

    private fun fetchPackagesFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val packageList = mutableListOf<TravelPackage>()
                for (childSnapshot in snapshot.children) {
                    val travelPackage = childSnapshot.getValue(TravelPackage::class.java)
                    travelPackage?.let { packageList.add(it) }
                }
                _packages.value = packageList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error (e.g., log)
            }
        })
    }

    fun getPackageById(id: String): TravelPackage? {
        return _packages.value.find { it.id == id }
    }
}
