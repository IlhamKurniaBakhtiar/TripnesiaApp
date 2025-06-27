package com.tripnesia.mobile.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import com.tripnesia.mobile.ui.component.DestinationDetailScreen
import com.tripnesia.mobile.ui.DestinationScreen
import com.tripnesia.mobile.viewmodel.DestinationViewModel

@Composable
fun NavigationDestination(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "destination") {
        composable("destination") {
            val viewModel: DestinationViewModel = viewModel()
            DestinationScreen(
                viewModel = viewModel,
                onDestinationClick = { destination ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("destination", destination)
                    navController.navigate("detail")
                }
            )
        }

        composable("detail") {
            val destination = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Destination>("destination")

            if (destination != null) {
                DestinationDetailScreen(destination = destination,
                    onBack = { navController.popBackStack() })
            }
        }
    }
}
