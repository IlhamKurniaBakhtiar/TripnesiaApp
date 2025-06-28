// LOKASI: app/src/main/java/com/tripnesia/mobile/ui/ForgotPasswordScreen.kt
// KODE LENGKAP DENGAN DESAIN YANG DIREVISI

package com.tripnesia.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripnesia.mobile.R
import com.tripnesia.mobile.viewmodel.ProfileViewModel

// Definisikan warna-warna kustom
private val cardBackgroundColor = Color(0xFFF5F1E9)
private val textFieldBackgroundColor = Color(0xFFE8E2D9)
private val buttonColor = Color(0xFF1E6A6B)
private val darkTextColor = Color(0xFF333333)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    viewModel: ProfileViewModel,
    onEmailSent: () -> Unit,
    onNavigateBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Latar belakang gambar fullscreen
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Kartu Form di tengah
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBackgroundColor)
                    .padding(horizontal = 24.dp, vertical = 32.dp) // Beri padding vertikal lebih besar
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_tripnesia),
                    contentDescription = "Tripnesia Logo",
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.height(24.dp)) // Spacer lebih besar

                Text(
                    text = "Lupa Password Anda?",
                    fontSize = 26.sp, // Ukuran font diperbesar
                    fontWeight = FontWeight.Bold,
                    color = darkTextColor,
                    textAlign = TextAlign.Center // Perataan di tengah
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Masukkan email Anda agar kami dapat mengirimkan tautan reset password.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center // Perataan di tengah
                )

                Spacer(modifier = Modifier.height(32.dp)) // Spacer lebih besar

                // TextField untuk Email
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    // Label dihilangkan dari atas, menjadi placeholder
                    placeholder = { Text("Email (contoh: nama@email.com)") },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = textFieldBackgroundColor,
                        unfocusedContainerColor = textFieldBackgroundColor,
                        disabledContainerColor = textFieldBackgroundColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = darkTextColor,
                        focusedTextColor = darkTextColor,
                        unfocusedTextColor = darkTextColor
                    )
                )

                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(top=8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.sendPasswordResetEmail(email)
                        onEmailSent() // Kembali ke login setelah diklik
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isLoading && email.isNotBlank(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Kirim Email Reset Password", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onNavigateBackToLogin) {
                    Text("Kembali ke Login", color = darkTextColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}