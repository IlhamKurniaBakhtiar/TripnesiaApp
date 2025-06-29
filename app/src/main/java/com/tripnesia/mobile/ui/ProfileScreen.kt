package com.tripnesia.mobile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tripnesia.mobile.R
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    var isEditMode by remember { mutableStateOf(false) }

    val originalName = remember { mutableStateOf("") }
    val originalEmail = remember { mutableStateOf("") }
    val originalImagePath = remember { mutableStateOf<String?>(null) }
    val originalPhoneNumber = remember { mutableStateOf("") }

    val needsReauth by viewModel.needsReauthentication

    if (needsReauth) {
        ReauthenticationDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.needsReauthentication.value = false },
            newName = viewModel.name.value,
            newEmail = viewModel.email.value,
            newPhoneNumber = viewModel.phoneNumber.value
        )
    }

    LaunchedEffect(isEditMode) {
        if (isEditMode) {
            originalName.value = viewModel.name.value
            originalEmail.value = viewModel.email.value
            originalImagePath.value = viewModel.profileImagePath.value
            originalPhoneNumber.value = viewModel.phoneNumber.value
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Profile" else "Profile") },
                actions = {
                    if (!isEditMode) {
                        IconButton(onClick = { isEditMode = true }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                        }
                    }
                },
                navigationIcon = {
                    if (isEditMode) {
                        IconButton(onClick = {
                            // Kembalikan data ke kondisi asli jika dibatalkan
                            viewModel.name.value = originalName.value
                            viewModel.email.value = originalEmail.value
                            viewModel.phoneNumber.value = originalPhoneNumber.value
                            viewModel.newProfileImageUri.value = null // Hapus preview gambar baru
                            isEditMode = false
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Cancel Edit")
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isEditMode) {
                EditProfileContent(
                    viewModel = viewModel,
                    onSaveClicked = { isEditMode = false },
                    onCancelClicked = {
                        // Kembalikan data ke kondisi asli jika dibatalkan
                        viewModel.name.value = originalName.value
                        viewModel.email.value = originalEmail.value
                        viewModel.phoneNumber.value = originalPhoneNumber.value
                        viewModel.newProfileImageUri.value = null
                        isEditMode = false
                    }
                )
            } else {
                ViewProfileContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ViewProfileContent(viewModel: ProfileViewModel) {
    val name by remember { viewModel.name }
    val email by remember { viewModel.email }
    val imagePath by remember { viewModel.profileImagePath }
    val phoneNumber by remember { viewModel.phoneNumber }
    val primaryColor = Color(0xFF003366)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, primaryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (!imagePath.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = File(imagePath!!)),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profilekosong),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(20.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(label = "Nama", value = name.ifEmpty { "Belum diatur" })
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow(label = "Email", value = email.ifEmpty { "Belum diatur" })
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow(label = "Nomor Telepon", value = phoneNumber.ifEmpty { "Nomor belum diisi" })

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Sign Out", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(viewModel: ProfileViewModel, onSaveClicked: () -> Unit, onCancelClicked: () -> Unit) {
    val name = viewModel.name
    val email = viewModel.email
    val phoneNumber = viewModel.phoneNumber
    val newImageUri by viewModel.newProfileImageUri
    val existingImagePath by viewModel.profileImagePath

    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.setNewProfileImageUri(it) }
    }

    val primaryColor = Color(0xFF003366)
    val accentColor = Color(0xFFFFC107)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .border(4.dp, primaryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val imageModel: Any? = newImageUri ?: if (!existingImagePath.isNullOrEmpty()) File(existingImagePath!!) else null
            if (imageModel != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageModel),
                    contentDescription = "Profile Image Preview",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profilekosong),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(20.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Button(
            onClick = { getImage.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text("Ubah Foto Profil", color = Color.Black)
        }

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCancelClicked,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Batal", color = Color.White)
            }
            Button(
                onClick = {
                    viewModel.updateProfile(name.value, email.value, phoneNumber.value)
                    onSaveClicked()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text("Simpan", color = Color.White)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color(0xFF003366)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReauthenticationDialog(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit,
    newName: String,
    newEmail: String,
    newPhoneNumber: String
) {
    var password by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.reauthErrorMessage

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Identitas") },
        text = {
            Column {
                Text("Untuk keamanan, silakan masukkan password Anda sekali lagi untuk menyimpan perubahan.")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; viewModel.reauthErrorMessage.value = null },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = errorMessage != null,
                    singleLine = true
                )
                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.reauthenticateAndRetryUpdate(password, newName, newEmail, newPhoneNumber)
                },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp))
                } else {
                    Text("Konfirmasi")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}