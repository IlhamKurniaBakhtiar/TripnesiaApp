package com.tripnesia.mobile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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

    // State untuk menyimpan data asli sebelum diedit
    val originalName = remember { mutableStateOf("") }
    val originalEmail = remember { mutableStateOf("") }
    val originalImagePath = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isEditMode) {
        if (!isEditMode) {
            // Saat beralih ke mode lihat, simpan data terbaru dari ViewModel
            originalName.value = viewModel.name.value
            originalEmail.value = viewModel.email.value
            originalImagePath.value = viewModel.profileImagePath.value
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
                            // Batalkan perubahan jika menekan tombol kembali
                            viewModel.name.value = originalName.value
                            viewModel.email.value = originalEmail.value
                            viewModel.profileImagePath.value = originalImagePath.value
                            viewModel.newProfileImageUri.value = null // Hapus preview gambar baru
                            isEditMode = false
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                    onSaveClicked = {
                        isEditMode = false
                    },
                    onCancelClicked = {
                        // Batalkan perubahan
                        viewModel.name.value = originalName.value
                        viewModel.email.value = originalEmail.value
                        viewModel.profileImagePath.value = originalImagePath.value
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
            // Cek jika path gambar tidak kosong
            if (!imagePath.isNullOrEmpty()) {
                // Muat gambar dari path file lokal
                Image(
                    painter = rememberAsyncImagePainter(model = File(imagePath!!)),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Tampilkan gambar default jika path kosong
                Image(
                    painter = painterResource(id = R.drawable.profilekosong),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.fillMaxSize().clip(CircleShape).padding(20.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(label = "Nama", value = name.ifEmpty { "Belum diatur" })
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow(label = "Email", value = email.ifEmpty { "Belum diatur" })

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
    val newImageUri by viewModel.newProfileImageUri
    val existingImagePath by viewModel.profileImagePath

    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.setNewProfileImage(it)
        }
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
            // Tentukan model gambar: prioritaskan URI baru, jika tidak ada, gunakan path lama
            val imageModel: Any? = newImageUri ?: if (!existingImagePath.isNullOrEmpty()) File(existingImagePath!!) else null

            if (imageModel != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageModel),
                    contentDescription = "Profile Image Preview",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profilekosong),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.fillMaxSize().clip(CircleShape).padding(20.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Button(
            onClick = { getImage.launch("image/*") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
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
            singleLine = true
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
                    viewModel.saveProfileChanges(name.value, email.value)
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