package com.tripnesia.mobile.viewmodel

import android.content.Context
import android.net.Uri
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel(context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ProfileData", Context.MODE_PRIVATE)

    val name: MutableState<String> = mutableStateOf(sharedPreferences.getString("name", "") ?: "")
    val email: MutableState<String> = mutableStateOf(sharedPreferences.getString("email", "") ?: "")
    val profileImageUri: MutableState<Uri?> = mutableStateOf(null)

    fun saveProfileData(name: String, email: String) {
        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("email", email)
            apply()
        }
        this.name.value = name
        this.email.value = email
    }

    fun updateProfileImage(uri: Uri?) {
        profileImageUri.value = uri
    }
}
