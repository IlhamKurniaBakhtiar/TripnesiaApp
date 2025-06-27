package com.tripnesia.mobile.ui.screen.paket

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.viewmodel.PackageViewModel

@Composable
fun NavigationPackage(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "packages") {

        composable("packages") {
            val viewModel: PackageViewModel = viewModel()
            PackageScreen(
                viewModel = viewModel,
                onPackageClick = { travelPackage ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("package", travelPackage)
                    navController.navigate("package_detail")
                }
            )
        }

        composable("package_detail") {
            val travelPackage = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<TravelPackage>("package")

            if (travelPackage != null) {
                PackageDetailScreen(
                    travelPackage = travelPackage,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
