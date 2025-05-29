package com.tripnesia.mobile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
// Hapus impor viewModel jika sudah di-pass sebagai parameter dan tidak dibuat ulang di sini
// import androidx.lifecycle.viewmodel.compose.viewModel
// import com.tripnesia.mobile.viewmodel.ProfileViewModelFactory
import com.tripnesia.mobile.R // Pastikan Anda memiliki resource drawable yang benar
import com.tripnesia.mobile.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    var isEditMode by remember { mutableStateOf(false) }

    // State sementara untuk edit, agar tidak langsung mengubah ViewModel sebelum disimpan
    // Ini berguna jika pengguna membatalkan edit, perubahan tidak akan tercermin.
    // Namun, untuk kasus ini, kita akan membiarkan ViewModel diupdate langsung,
    // dan 'save' hanya akan memicu persistensi (misalnya ke SharedPreferences).
    // Jika ingin revert-on-cancel, Anda perlu state tambahan di sini.

    val originalName = remember { mutableStateOf("") }
    val originalEmail = remember { mutableStateOf("") }
    val originalImageUri = remember { mutableStateOf<Uri?>(null) }

    // Simpan nilai asli saat beralih ke mode edit atau saat data viewModel berubah
    LaunchedEffect(viewModel.name.value, viewModel.email.value, viewModel.profileImageUri.value, isEditMode) {
        if (!isEditMode) { // Atau saat data dari viewModel pertama kali dimuat
            originalName.value = viewModel.name.value
            originalEmail.value = viewModel.email.value
            originalImageUri.value = viewModel.profileImageUri.value
        }
    }


    Scaffold(
        floatingActionButton = {
            if (!isEditMode) {
                FloatingActionButton(
                    onClick = {
                        // Simpan state saat ini sebelum masuk mode edit
                        originalName.value = viewModel.name.value
                        originalEmail.value = viewModel.email.value
                        originalImageUri.value = viewModel.profileImageUri.value
                        isEditMode = true
                    },
                    containerColor = Color(0xFFFFC107) // accentColor
                ) {
                    Icon(Icons.Filled.Edit, "Edit Profile", tint = Color.Black)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            if (isEditMode) {
                EditProfileContent(
                    viewModel = viewModel,
                    onSaveClicked = {
                        viewModel.saveProfileData(viewModel.name.value, viewModel.email.value) // Simpan data dari ViewModel
                        isEditMode = false
                    },
                    onCancelClicked = {
                        // Kembalikan nilai ViewModel ke nilai asli sebelum edit
                        viewModel.name.value = originalName.value
                        viewModel.email.value = originalEmail.value
                        viewModel.profileImageUri.value = originalImageUri.value // Ini akan memicu recomposition jika Uri berubah
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
    // Menggunakan .collectAsState() jika name, email, profileImageUri adalah StateFlow
    // Jika sudah MutableState<String> di ViewModel, cukup akses .value
    val name by remember { viewModel.name }
    val email by remember { viewModel.email }
    val profileImageUri by remember { viewModel.profileImageUri }

    val primaryColor = Color(0xFF003366) // blueDark

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Beri jarak dari atas

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, primaryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            profileImageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                // Gunakan placeholder yang lebih sesuai jika ada, atau default system icon
                Image(
                    painter = painterResource(id = R.drawable.profilekosong), // Ganti dengan placeholder Anda
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(20.dp), // Padding agar icon tidak terlalu besar
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(label = "Nama", value = name.ifEmpty { "Belum diatur" })
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoRow(label = "Email", value = email.ifEmpty { "Belum diatur" })
        // Tambahkan info lain jika ada
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
            color = Color(0xFF003366) // blueDark
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    viewModel: ProfileViewModel,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    // State ini sekarang dikelola oleh ViewModel dan di-pass ke ProfileScreen
    val name = viewModel.name // Ini adalah MutableState<String>
    val email = viewModel.email // Ini adalah MutableState<String>
    val profileImageUri = viewModel.profileImageUri // Ini adalah MutableState<Uri?>


    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updateProfileImage(it)
        }
    }

    val primaryColor = Color(0xFF003366)
    val accentColor = Color(0xFFFFC107)
    val context = LocalContext.current // Untuk Toast atau keperluan context lain jika ada

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
            profileImageUri.value?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Image(
                    painter = painterResource(id = R.drawable.profilekosong), // Ganti dengan placeholder Anda
                    contentDescription = "Default Image",
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
            placeholder = { Text("Masukkan nama Anda", color = primaryColor.copy(alpha = 0.7f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            placeholder = { Text("Masukkan email Anda", color = primaryColor.copy(alpha = 0.7f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor
            )
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
                    // Validasi sederhana bisa ditambahkan di sini jika perlu
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