package com.tripnesia.mobile.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tripnesia.mobile.data.model.Destination
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.util.Log
import coil.compose.AsyncImage
import com.tripnesia.mobile.ui.theme.primaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailScreen(destination: Destination, onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = destination.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) // Apply padding from Scaffold
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Consistent horizontal padding
        ) {
            // Destination Image
            Image(
                painter = painterResource(id = destination.imageRes),
                contentDescription = destination.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(8.dp)), // Rounded corners for the image
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(8.dp))

            // Destination Description
            Text(
                text = destination.description,
                style = MaterialTheme.typography.bodyLarge, // Slightly larger body text
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Justify
            )

            Text("📍 Lokasi: ${destination.location}", style = MaterialTheme.typography.bodyMedium)
            Text("⭐ Rating: ${destination.rating}/5", style = MaterialTheme.typography.bodyMedium)
            Text("💰 Harga: ${destination.price}", style = MaterialTheme.typography.bodyMedium)
            Text("🕒 Jam Buka: ${destination.openingHours}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intentUri = Uri.parse("geo:0,0?q=${Uri.encode(destination.placeQuery)}")
                    val intent = Intent(Intent.ACTION_VIEW, intentUri)
                    intent.setPackage("com.google.android.apps.maps")
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue , // warna background tombol
                    contentColor = Color.White         // warna teks tombol
                )
            ) {
                Text("Buka di Google Maps")
            }
            // You can add more details here, e.g., location, ratings, etc.
            // Spacer(modifier = Modifier.height(16.dp))
            // Text(text = "Lokasi: ${destination.location}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
