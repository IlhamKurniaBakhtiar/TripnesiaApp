package com.tripnesia.mobile.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tripnesia.mobile.data.db.AppDatabase
import com.tripnesia.mobile.data.model.Event
import com.tripnesia.mobile.data.repository.EventRepository
import com.tripnesia.mobile.data.dummy.sampleEvents
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).eventDao()
    private val repository = EventRepository(dao)

    val events: StateFlow<List<Event>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedEvent = kotlinx.coroutines.flow.MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun clearSelection() {
        _selectedEvent.value = null
    }

    // dipanggil satu kali di awal (misal pakai LaunchedEffect)
    fun insertDummyEvents() {
        viewModelScope.launch {
            repository.insertDummyData(sampleEvents)
        }
    }
}
