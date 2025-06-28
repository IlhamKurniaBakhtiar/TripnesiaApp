// LOKASI: app/src/main/java/com/tripnesia/mobile/viewmodel/ProfileViewModel.kt
// KODE LENGKAP YANG SUDAH DIPERBAIKI DENGAN FUNGSI LOGIN & REGISTER

package com.tripnesia.mobile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(private val context: Context) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val database: DatabaseReference = Firebase.database.getReference("users")

    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val profileImagePath = mutableStateOf<String?>(null)
    val newProfileImageUri = mutableStateOf<Uri?>(null)

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                _isLoggedIn.value = true
                fetchUserProfile()
            } else {
                _isLoggedIn.value = false
                clearUserData()
            }
        }
    }

    // === FUNGSI YANG HILANG, DITAMBAHKAN KEMBALI DI SINI ===

    fun login(userEmail: String, userPass: String) {
        isLoading.value = true
        errorMessage.value = null
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(userEmail, userPass).await()
                // Jika berhasil, AuthStateListener akan otomatis menangani update UI
            } catch (e: Exception) {
                errorMessage.value = "Login Gagal: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun register(newName: String, newEmail: String, newPass: String) {
        isLoading.value = true
        errorMessage.value = null
        viewModelScope.launch {
            try {
                // 1. Buat user di Firebase Authentication
                val result = auth.createUserWithEmailAndPassword(newEmail, newPass).await()
                val user = result.user
                if (user != null) {
                    // 2. Siapkan data awal untuk disimpan di Realtime Database
                    val userData = hashMapOf(
                        "name" to newName,
                        "email" to newEmail,
                        "profileImagePath" to null // Awalnya tidak ada gambar
                    )
                    database.child(user.uid).setValue(userData).await()
                    // Langsung logout agar user login manual untuk pertama kali
                    auth.signOut()
                }
            } catch (e: Exception) {
                errorMessage.value = "Registrasi Gagal: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // === BATAS AKHIR FUNGSI YANG DITAMBAHKAN ===


    private fun fetchUserProfile() {
        viewModelScope.launch {
            val user = auth.currentUser ?: return@launch
            try {
                isLoading.value = true
                val snapshot = database.child(user.uid).get().await()
                if (snapshot.exists()) {
                    name.value = snapshot.child("name").getValue(String::class.java) ?: ""
                    email.value = snapshot.child("email").getValue(String::class.java) ?: ""
                    profileImagePath.value = snapshot.child("profileImagePath").getValue(String::class.java)
                }
            } catch (e: Exception) {
                errorMessage.value = "Gagal memuat profil: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setNewProfileImage(uri: Uri) {
        newProfileImageUri.value = uri
    }

    fun saveProfileChanges(newName: String, newEmail: String) {
        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = auth.currentUser ?: throw Exception("User not logged in")
                var finalImagePath: String? = profileImagePath.value

                newProfileImageUri.value?.let { uri ->
                    finalImagePath = saveImageToInternalStorage(uri, user.uid)
                }

                updateUserData(user, newName, newEmail, finalImagePath)

                withContext(Dispatchers.Main) {
                    newProfileImageUri.value = null
                }

                if (user.email != newEmail) {
                    user.updateEmail(newEmail).await()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage.value = "Gagal menyimpan: ${e.message}"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                }
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri, userId: String): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "profile_image_$userId.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }

    private suspend fun updateUserData(user: com.google.firebase.auth.FirebaseUser, newName: String, newEmail: String, imagePath: String?) {
        val userData = mapOf(
            "name" to newName,
            "email" to newEmail,
            "profileImagePath" to imagePath
        )
        database.child(user.uid).updateChildren(userData).await()
        withContext(Dispatchers.Main) {
            fetchUserProfile()
        }
    }

    private fun clearUserData() {
        name.value = ""
        email.value = ""
        profileImagePath.value = null
        newProfileImageUri.value = null
    }

    fun logout() {
        auth.signOut()
    }
}