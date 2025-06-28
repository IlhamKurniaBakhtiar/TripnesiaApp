package com.tripnesia.mobile.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tripnesia.mobile.ui.components.BottomNavItem
import com.tripnesia.mobile.ui.screen.paket.NavigationPackage
import com.tripnesia.mobile.ui.theme.primaryBlue
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import com.tripnesia.mobile.viewmodel.ProfileViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripnesiaMainScreen() {
    val context = LocalContext.current
    // Inisialisasi ViewModel menggunakan Factory untuk memberikan Context
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(context))

    val window = (context as? Activity)?.window
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window?.statusBarColor = Color(0xFF0C1222).toArgb()
        window?.let { WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false }
    }

    var selectedItemIndex by remember { mutableStateOf(0) }

    val navItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Event", Icons.Filled.CalendarMonth),
        BottomNavItem("Destinasi", Icons.Filled.Map),
        BottomNavItem("Travel", Icons.Filled.CardTravel),
        BottomNavItem("Profile", Icons.Filled.Person)
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = primaryBlue) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                tint = if (selectedItemIndex == index) Color.Black else Color.White
                            )
                        },
                        label = {
                            Text(item.label, color = Color.White)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItemIndex) {
                0 -> HomeScreen()
                1 -> EventScreen()
                2 -> NavigationDestination(navController = navController)
                3 -> NavigationPackage(navController = navController)
                4 -> AuthNavigation(viewModel = viewModel)
            }
        }
    }
}