// LOKASI: app/src/main/java/com/tripnesia/mobile/ui/RegisterScreen.kt
// KODE LENGKAP DAN FINAL

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripnesia.mobile.R
import com.tripnesia.mobile.viewmodel.ProfileViewModel

// Definisikan warna-warna kustom agar mudah diubah
private val cardBackgroundColor = Color(0xFFF5F1E9)
private val textFieldBackgroundColor = Color(0xFFE8E2D9)
private val buttonColor = Color(0xFF1E6A6B)
private val darkTextColor = Color(0xFF333333)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: ProfileViewModel,
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    var passwordMismatchError by remember { mutableStateOf(false) }

    // Latar belakang gambar fullscreen
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Kartu Form Registrasi di tengah
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Kolom ini bisa di-scroll agar tidak tertutup keyboard
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBackgroundColor)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_tripnesia),
                    contentDescription = "Tripnesia Logo",
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Buat Akun Tripnesia",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkTextColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Custom TextField untuk Nama Lengkap
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Nama Lengkap") },
                    shape = RoundedCornerShape(8.dp),
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

                Spacer(modifier = Modifier.height(16.dp))

                // Custom TextField untuk Email
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Email") },
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

                Spacer(modifier = Modifier.height(16.dp))

                // Custom TextField untuk Password
                TextField(
                    value = password,
                    onValueChange = { password = it; passwordMismatchError = false },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Password") },
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

                Spacer(modifier = Modifier.height(16.dp))

                // Custom TextField untuk Konfirmasi Password
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; passwordMismatchError = false },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Konfirmasi Password") },
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = textFieldBackgroundColor,
                        unfocusedContainerColor = textFieldBackgroundColor,
                        disabledContainerColor = textFieldBackgroundColor,
                        errorContainerColor = textFieldBackgroundColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = darkTextColor,
                        focusedTextColor = darkTextColor,
                        unfocusedTextColor = darkTextColor
                    ),
                    isError = passwordMismatchError
                )

                if (passwordMismatchError) {
                    Text(text = "Password tidak cocok!", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(top=4.dp))
                }

                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || email.isEmpty()) {
                            // Anda bisa menambahkan pesan error spesifik di sini jika mau
                        } else if (password == confirmPassword) {
                            viewModel.register(name, email, password)
                            onRegistrationSuccess()
                        } else {
                            passwordMismatchError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Daftar", color = Color.White, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onNavigateToLogin) {
                    Text("Sudah punya akun? Login", color = darkTextColor, fontSize = 14.sp)
                }
            }
        }
    }
}