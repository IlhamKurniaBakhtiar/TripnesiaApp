package com.tripnesia.mobile.ui

import android.content.Context
import android.net.Uri
import android.content.SharedPreferences
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(context: Context) {
    // SharedPreferences
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("ProfileData", Context.MODE_PRIVATE)
    var name by remember { mutableStateOf(TextFieldValue(sharedPreferences.getString("name", "") ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(sharedPreferences.getString("email", "") ?: "")) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Registering the gallery activity result launcher
    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        profileImageUri = uri
    }

    // Colors
    val primaryColor = Color(0xFF003366) // Blue
    val accentColor = Color(0xFFFFC107) // Yellow/Orange

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile Image Box (with border)
        Box(
            modifier = Modifier
                .size(150.dp) // Set the size of the profile image (ensures square shape)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
                .clip(CircleShape) // Clip the image and border into a circle
                .border(4.dp, primaryColor, CircleShape), // Add a thicker border around the image
            contentAlignment = Alignment.Center
        ) {
            profileImageUri?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape), // Clip image into circle shape
                    contentScale = ContentScale.Crop // Ensure the image is cropped to fit inside the circle
                )
            } ?: run {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape), // Clip default image into circle shape
                    contentScale = ContentScale.Crop // Ensure the image is cropped to fit inside the circle
                )
            }
        }

        Button(
            onClick = { getImage.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text("Change Profile Picture", color = Color.Black)
        }

        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = null,  // Tidak ada label
            placeholder = { Text("Enter your name", color = primaryColor.copy(alpha = 0.7f)) }, // Tambahkan placeholder
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = null,  // Tidak ada label
            placeholder = { Text("Enter your email", color = primaryColor.copy(alpha = 0.7f)) }, // Tambahkan placeholder
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Button(
            onClick = {
                // Save the changes to profile info
                with(sharedPreferences.edit()) {
                    putString("name", name.text)
                    putString("email", email.text)
                    apply()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text("Save Changes", color = Color.White)
        }
    }
}
