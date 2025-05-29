package com.tripnesia.mobile.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.tripnesia.mobile.data.dummy.sampleEvents
import com.tripnesia.mobile.data.model.Event


class EventViewModel : ViewModel() {

    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    private val _events = MutableStateFlow(sampleEvents)
    val events: StateFlow<List<Event>> = _events

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun clearSelection() {
        _selectedEvent.value = null
    }
}
