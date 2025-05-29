package com.tripnesia.mobile.viewmodel

import androidx.lifecycle.ViewModel
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DestinationViewModel : ViewModel() {

    // private = tidak bisa diubah dari luar
    private val _destinations = MutableStateFlow(DestinationData.destinations)
    val destinations: StateFlow<List<Destination>> = _destinations

    fun getDestinationById(id: Int): Destination? {
        return _destinations.value.find { it.id == id }
    }
}
