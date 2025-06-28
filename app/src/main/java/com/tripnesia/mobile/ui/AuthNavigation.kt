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
                    },
                    onNavigateToForgotPassword = {
                        navController.navigate("forgot_password")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = viewModel,
                    onRegistrationSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable("forgot_password") {
                ForgotPasswordScreen(
                    viewModel = viewModel,
                    onEmailSent = {
                        navController.popBackStack()
                    },
                    onNavigateBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
