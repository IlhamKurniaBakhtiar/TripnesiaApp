package com.tripnesia.mobile.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
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
    private val TAG = "PROFILE_VM_DEBUG" // Tag untuk filter di Logcat

    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    val profileImagePath = mutableStateOf<String?>(null)

    val newProfileImageUri = mutableStateOf<Uri?>(null)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val needsReauthentication = mutableStateOf(false)
    val reauthErrorMessage = mutableStateOf<String?>(null)
    val passwordResetEmailSent = mutableStateOf(false)

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

    fun updateProfile(newName: String, newEmail: String, newPhoneNumber: String) {
        isLoading.value = true
        errorMessage.value = null
        Log.d(TAG, "--- Mulai updateProfile ---")
        Log.d(TAG, "Data Diterima: Name='$newName', Email='$newEmail', Phone='$newPhoneNumber'")

        viewModelScope.launch {
            val user = auth.currentUser ?: run {
                errorMessage.value = "Pengguna tidak ditemukan."
                isLoading.value = false
                Log.e(TAG, "Gagal: User null saat akan update.")
                return@launch
            }

            if (user.email != newEmail) {
                Log.d(TAG, "Email berbeda. Mencoba update email di Firebase Auth...")
                try {
                    user.updateEmail(newEmail).await()
                    Log.d(TAG, "SUKSES: Email di Firebase Auth berhasil diubah ke '$newEmail'")
                } catch (e: FirebaseAuthRecentLoginRequiredException) {
                    Log.w(TAG, "GAGAL: Butuh re-autentikasi untuk mengubah email. Menampilkan dialog...")
                    needsReauthentication.value = true
                    isLoading.value = false
                    return@launch
                } catch (e: Exception) {
                    Log.e(TAG, "GAGAL: Terjadi error saat mengubah email di Auth.", e)
                    errorMessage.value = "Gagal mengubah email: ${e.message}"
                    isLoading.value = false
                    return@launch
                }
            } else {
                Log.d(TAG, "Email tidak berubah, melewati update email di Auth.")
            }

            Log.d(TAG, "Melanjutkan ke penyimpanan data ke Realtime Database...")
            saveAllDataToDatabase(newName, newEmail, newPhoneNumber)
        }
    }

    fun reauthenticateAndRetryUpdate(password: String, newName: String, newEmail: String, newPhoneNumber: String) {
        isLoading.value = true
        reauthErrorMessage.value = null
        Log.d(TAG, "--- Mulai reauthenticateAndRetryUpdate ---")
        viewModelScope.launch {
            try {
                val user = auth.currentUser ?: throw Exception("User not found")
                val credential = EmailAuthProvider.getCredential(user.email!!, password)
                Log.d(TAG, "Mencoba re-autentikasi untuk user: ${user.email}")
                user.reauthenticate(credential).await()
                Log.d(TAG, "SUKSES: Re-autentikasi berhasil.")

                needsReauthentication.value = false
                Log.d(TAG, "Memanggil ulang updateProfile setelah re-autentikasi...")
                updateProfile(newName, newEmail, newPhoneNumber)

            } catch (e: Exception) {
                Log.e(TAG, "GAGAL: Re-autentikasi gagal.", e)
                reauthErrorMessage.value = "Password salah. Coba lagi."
            } finally {
                isLoading.value = false
            }
        }
    }

    private suspend fun saveAllDataToDatabase(newName: String, newEmail: String, newPhoneNumber: String) {
        val user = auth.currentUser ?: return
        try {
            Log.d(TAG, "Memulai saveAllDataToDatabase...")
            val finalImagePath: String? = withContext(Dispatchers.IO) {
                newProfileImageUri.value?.let { uri ->
                    Log.d(TAG, "Ada gambar baru, menyimpan ke internal storage...")
                    saveImageToInternalStorage(uri, user.uid)
                } ?: profileImagePath.value
            }

            val userData = mapOf(
                "name" to newName,
                "email" to newEmail,
                "phoneNumber" to newPhoneNumber,
                "profileImagePath" to finalImagePath
            )
            Log.d(TAG, "Data yang akan ditulis ke DB: $userData")

            database.child(user.uid).updateChildren(userData).await()
            Log.d(TAG, "SUKSES: Penulisan data ke Realtime Database berhasil.")

            newProfileImageUri.value = null
            fetchUserProfile()
        } catch (e: Exception) {
            Log.e(TAG, "GAGAL: Terjadi error saat menulis ke Realtime Database.", e)
            errorMessage.value = "Gagal menyimpan data ke database: ${e.message}"
        } finally {
            isLoading.value = false
            Log.d(TAG, "--- Proses updateProfile Selesai ---")
        }
    }

    fun setNewProfileImageUri(uri: Uri) {
        newProfileImageUri.value = uri
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val user = auth.currentUser ?: return@launch
            val snapshot = database.child(user.uid).get().await()
            if (snapshot.exists()) {
                name.value = snapshot.child("name").getValue(String::class.java) ?: ""
                email.value = snapshot.child("email").getValue(String::class.java) ?: ""
                phoneNumber.value = snapshot.child("phoneNumber").getValue(String::class.java) ?: ""
                profileImagePath.value = snapshot.child("profileImagePath").getValue(String::class.java)
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri, userId: String): String? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = File(context.filesDir, "profile_image_$userId.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                file.absolutePath
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gagal menyimpan gambar", e)
            null
        }
    }

    private fun clearUserData() {
        name.value = ""; email.value = ""; phoneNumber.value = ""
        profileImagePath.value = null; newProfileImageUri.value = null
    }

    fun login(userEmail: String, userPass: String) {
        isLoading.value = true
        errorMessage.value = null
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(userEmail, userPass).await()
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
                val result = auth.createUserWithEmailAndPassword(newEmail, newPass).await()
                val user = result.user
                if (user != null) {
                    val userData = hashMapOf<String, Any?>(
                        "name" to newName,
                        "email" to newEmail,
                        "profileImagePath" to null,
                        "phoneNumber" to null
                    )
                    database.child(user.uid).setValue(userData).await()
                    auth.signOut()
                }
            } catch (e: Exception) {
                errorMessage.value = "Registrasi Gagal: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun sendPasswordResetEmail(email: String) {
        isLoading.value = true
        errorMessage.value = null
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
                Log.d(TAG, "Email reset password berhasil dikirim ke $email")
                passwordResetEmailSent.value = true
            } catch (e: Exception) {
                Log.e(TAG, "Gagal mengirim email reset password", e)
                errorMessage.value = "Gagal: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}

