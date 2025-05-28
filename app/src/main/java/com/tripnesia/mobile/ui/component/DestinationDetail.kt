package com.tripnesia.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tripnesia.mobile.ui.screen.Destination


@Composable
fun DestinationDetailScreen(destination: Destination) {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = destination.imageRes),
            contentDescription = destination.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = destination.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = destination.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
