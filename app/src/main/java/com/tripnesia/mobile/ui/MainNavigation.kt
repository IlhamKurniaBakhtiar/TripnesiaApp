package com.tripnesia.mobile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tripnesia.mobile.ui.AuthNavigation
import com.tripnesia.mobile.ui.screen.paket.NavigationPackage

@Composable
fun MainNavigation(viewModel: ProfileViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val mainNavController = rememberNavController()

    Box {
        if (isLoggedIn) {
            // Navigasi utama (fitur setelah login)
            NavigationPackage(navController = mainNavController)
        } else {
            // Navigasi auth (login, register, lupa password)
            AuthNavigation(viewModel = viewModel)
        }
    }
}