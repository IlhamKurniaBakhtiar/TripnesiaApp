package com.tripnesia.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripnesia.mobile.data.model.Event


@Composable
fun EventDetailScreen(event: Event, onBack: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = event.imageResId),
            contentDescription = event.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(event.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("${event.date} | ${event.kategori}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = event.description,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}
