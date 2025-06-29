package com.tripnesia.mobile.ui.screen.paket

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tripnesia.mobile.Database.getSnapToken
import com.tripnesia.mobile.R
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalLayoutApi::class,ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailScreen(
    travelPackage: TravelPackage,
    navController: NavController,
    onBack: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(travelPackage.imageUrl, "drawable", context.packageName)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Paket") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = travelPackage.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = travelPackage.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = travelPackage.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Duration",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${travelPackage.durationDays} hari",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFDD835),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${travelPackage.rating}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (travelPackage.facilities.isNotEmpty()) {
                    Text(
                        text = "Fasilitas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        travelPackage.facilities.forEach { facility ->
                            AssistChip(
                                onClick = { },
                                label = {
                                    Text(
                                        text = facility,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = "Deskripsi",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = travelPackage.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Harga Mulai Dari",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Rp${travelPackage.price}",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = onClick@{
                            val user = Firebase.auth.currentUser
                            if (user == null) {
                                Toast.makeText(context, "Silakan login terlebih dahulu melalui menu Profil", Toast.LENGTH_SHORT).show()
                                return@onClick
                            }

                            val userName = profileViewModel.name.value.ifBlank {
                                "Pengguna"
                            }
                            val userEmail = user.email ?: "user@example.com"
                            val orderId = "ORDER-${System.currentTimeMillis()}"

                            coroutineScope.launch {
                                try {
                                    val snapToken = getSnapToken(orderId, travelPackage.price, userName, userEmail)
                                    if (snapToken != null) {
                                        sendInvoice(userName, userEmail, orderId, travelPackage.price)
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
                        modifier = Modifier
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7F6AFF),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pesan Sekarang", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

fun sendInvoice(name: String, email: String, orderId: String, amount: Int) {
    val client = OkHttpClient()
    val json = JSONObject().apply {
        put("name", name)
        put("email", email)
        put("orderId", orderId)
        put("amount", amount)
    }

    val jsonString = json.toString()
    Log.d("INVOICE", "Sending invoice with payload: $jsonString")

    val body = jsonString.toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url("https://tripnesia-production.up.railway.app/send-invoice")
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("INVOICE", "Gagal kirim invoice: ${e.message}", e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("INVOICE", "Invoice berhasil dikirim! Status code: ${response.code}")
            } else {
                Log.e("INVOICE", "Gagal kirim invoice. Status: ${response.code}, body: ${response.body?.string()}")
            }
        }
    })
}