package com.tripnesia.mobile.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.tripnesia.mobile.data.model.TravelPackage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PackageViewModel : ViewModel() {

    // State untuk semua package
    private val _packages = MutableStateFlow<List<TravelPackage>>(emptyList())
    val packages: StateFlow<List<TravelPackage>> = _packages

    // State untuk 1 package (bisa digunakan untuk screen detail)
    private val _selectedPackage = MutableStateFlow<TravelPackage?>(null)
    val selectedPackage: StateFlow<TravelPackage?> = _selectedPackage

    // Referensi ke Firebase
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
                // Untuk debugging kalau ada error dari Firebase
                println(" Firebase error: ${error.message}")
            }
        })
    }

    // Fungsi biasa, hanya cari dari list
    fun getPackageById(id: String): TravelPackage? {
        return _packages.value.find { it.id == id }
    }

    // Fungsi reaktif: update _selectedPackage
    fun selectPackageById(id: String) {
        val selected = _packages.value.find { it.id == id }
        _selectedPackage.value = selected
    }

    // (Opsional) Kalau kamu ingin reset selected-nya
    fun clearSelectedPackage() {
        _selectedPackage.value = null
    }
}
