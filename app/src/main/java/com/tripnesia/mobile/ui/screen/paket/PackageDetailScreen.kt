package com.tripnesia.mobile.ui.screen.paket

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
import android.widget.Toast
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tripnesia.mobile.Database.getSnapToken
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.ui.theme.primaryBlue
import kotlinx.coroutines.coroutineScope
import java.net.URLEncoder
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailScreen(
    travelPackage: TravelPackage,
    navController: NavController,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageResId = remember(travelPackage.imageUrl) {
        context.resources.getIdentifier(travelPackage.imageUrl, "drawable", context.packageName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Paket") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF1A1B3F), // dark navy
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
        ) {
            // Gambar Utama
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = travelPackage.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            // Konten Utama
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = travelPackage.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = travelPackage.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Durasi: ${travelPackage.durationDays} hari")
                    Text(text = "⭐ ${travelPackage.rating}")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Harga: Rp${travelPackage.price}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = travelPackage.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol Pemesanan
                Button(
                    onClick = onClick@{
                        val user = Firebase.auth.currentUser
                        if (user == null) {
                            Toast.makeText(context, "Silakan login terlebih dahulu melalui menu Profil", Toast.LENGTH_SHORT).show()
                            return@onClick
                        }

                        val userName = user.displayName ?: user.email?.substringBefore("@") ?: "Pengguna"
                        val userEmail = user.email ?: "user@example.com"

                        coroutineScope.launch {
                            try {
                                val snapToken = getSnapToken(
                                    orderId = "ORDER-${System.currentTimeMillis()}",
                                    amount = travelPackage.price,
                                    name = userName,
                                    email = userEmail
                                )

                                if (snapToken != null) {
                                    val snapUrl = "https://app.sandbox.midtrans.com/snap/v2/vtweb/$snapToken"
                                    navController.navigate("payment_screen/${URLEncoder.encode(snapUrl, "UTF-8")}")
                                } else {
                                    Toast.makeText(context, "Gagal mendapatkan Snap Token", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(context, "Terjadi error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7F6AFF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Pesan Sekarang")
                }

            }
        }
    }
}
