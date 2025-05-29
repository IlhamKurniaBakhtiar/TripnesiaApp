package com.tripnesia.mobile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import com.tripnesia.mobile.ui.DestinationDetailScreen
import com.tripnesia.mobile.ui.DestinationScreen

@Composable
fun NavigationDestination(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "destination") {
        composable("destination") {
            DestinationScreen(
                destinations = DestinationData.destinations,
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
                DestinationDetailScreen(destination = destination)
            }
        }
    }
}
