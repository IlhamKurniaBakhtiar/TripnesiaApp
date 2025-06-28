package com.tripnesia.mobile.ui.screen.paket

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
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
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF1A1B3F),
                    titleContentColor = Color.White
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

            Column(modifier = Modifier.padding(16.dp)) {
                Text(travelPackage.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(travelPackage.location, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Durasi: ${travelPackage.durationDays} hari")
                    Text("⭐ ${travelPackage.rating}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Harga: Rp${travelPackage.price}", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(16.dp))
                Text(travelPackage.description, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(24.dp))

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
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F6AFF), contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pesan Sekarang")
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
        .url("https://tripnesia-production.up.railway.app/send-invoice") // Ganti sesuai domain backend kamu
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