package com.tripnesia.mobile.ui.screen.paket

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.viewmodel.PackageViewModel
import com.tripnesia.mobile.ui.payment.PaymentScreen

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
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "payment_screen/{snapUrl}",
            arguments = listOf(navArgument("snapUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("snapUrl") ?: ""
            val decodedUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8")

            PaymentScreen(
                snapUrl = decodedUrl,
                onPaymentFinished = {
                    navController.popBackStack()
                }
            )
        }
    }
}
