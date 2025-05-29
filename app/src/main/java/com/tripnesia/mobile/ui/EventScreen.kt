package com.tripnesia.mobile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tripnesia.mobile.data.model.Event

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()
    val selectedEvent by viewModel.selectedEvent.collectAsState()

    if (selectedEvent == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Events",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(events) { event ->
                    EventItem(event = event) {
                        viewModel.selectEvent(event)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Acara lainnya akan diupdate segera.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    )
                }
            }
        }
    } else {
        EventDetailScreen(event = selectedEvent!!) {
            viewModel.clearSelection()
        }
    }
}
