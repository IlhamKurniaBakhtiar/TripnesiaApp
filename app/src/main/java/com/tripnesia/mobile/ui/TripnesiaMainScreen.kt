package com.tripnesia.mobile.ui

import android.os.Build
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tripnesia.mobile.ui.theme.blueDark
import com.tripnesia.mobile.ui.components.BottomNavItem
import com.tripnesia.mobile.viewmodel.ProfileViewModel
import com.tripnesia.mobile.viewmodel.ProfileViewModelFactory// Impor ProfileViewModel dari folder viewmodel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripnesiaMainScreen() {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(context))  // Mendapatkan ViewModel dari factory

    // Mengatur status bar untuk Android M ke atas
    val window = (context as? Activity)?.window
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        window?.statusBarColor = Color(0xFF003366).toArgb()
        window?.let { WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false }
    }

    var selectedItemIndex by remember { mutableStateOf(0) }
    val navItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Event", Icons.Filled.CalendarMonth),
        BottomNavItem("Destinasi", Icons.Filled.LocationOn),
        BottomNavItem("Profile", Icons.Filled.Person) // Ganti 'Setting' menjadi 'Profile'
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = blueDark) {
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
                3 -> ProfileScreen(viewModel = viewModel)  // Kirimkan ViewModel ke ProfileScreen
            }
        }
    }
}
