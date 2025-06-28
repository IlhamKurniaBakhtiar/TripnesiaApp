package com.tripnesia.mobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tripnesia.mobile.viewmodel.ProfileViewModel

@Composable
fun AuthNavigation(viewModel: ProfileViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val navController = rememberNavController()

    if (isLoggedIn) {
        ProfileScreen(viewModel = viewModel)
    } else {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateToRegister = {
                        navController.navigate("register")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = viewModel,
                    onRegistrationSuccess = {
                        // Kembali ke halaman login setelah registrasi
                        navController.popBackStack()
                    },
                    // Tambahkan aksi untuk tombol baru
                    onNavigateToLogin = {
                        // Kembali ke halaman login jika tombol diklik
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}