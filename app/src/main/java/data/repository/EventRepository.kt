package com.tripnesia.mobile.data.repository

import com.tripnesia.mobile.data.db.EventDao
import com.tripnesia.mobile.data.model.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) {
    fun getAll(): Flow<List<Event>> = dao.getAll()
    suspend fun insertDummyData(events: List<Event>) {
        dao.clear()
        dao.insertAll(events)
    }
}
