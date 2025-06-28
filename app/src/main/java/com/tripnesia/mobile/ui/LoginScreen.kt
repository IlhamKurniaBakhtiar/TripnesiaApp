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
import kotlinx.coroutines.launch

private val cardBackgroundColor = Color(0xFFF5F1E9)
private val textFieldBackgroundColor = Color(0xFFE8E2D9)
private val buttonColor = Color(0xFF1E6A6B)
private val darkTextColor = Color(0xFF333333)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: ProfileViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val passwordResetSent by viewModel.passwordResetEmailSent

    LaunchedEffect(email, password) {
        if (errorMessage != null) {
            viewModel.errorMessage.value = null
        }
    }

    LaunchedEffect(passwordResetSent) {
        if (passwordResetSent) {
            scope.launch {
                snackbarHostState.showSnackbar("Link reset password telah dikirim ke email Anda.")
            }
            viewModel.passwordResetEmailSent.value = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Image(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
                        text = "Selamat Datang!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkTextColor
                    )

                    Text(
                        text = "Login untuk melanjutkan ke Tripnesia",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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

                    TextField(
                        value = password,
                        onValueChange = { password = it },
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

                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = onNavigateToForgotPassword,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text("Lupa Password?", color = darkTextColor, fontSize = 12.sp)
                        }
                    }

                    if (errorMessage != null && !isLoading) {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.login(email, password) },
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
                            Text("Login", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateToRegister) {
                        Text("Belum punya akun? Daftar di sini", color = darkTextColor, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}