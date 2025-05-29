package com.tripnesia.mobile.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Pengecekan tipe modelClass untuk memastikan tipe yang sesuai
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(context) as T  // Pastikan cast yang aman
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
