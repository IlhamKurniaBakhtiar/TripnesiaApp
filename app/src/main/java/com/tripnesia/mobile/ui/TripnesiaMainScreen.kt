package com.tripnesia.mobile.ui

import DestinationScreen
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.tripnesia.mobile.ui.theme.blueDark
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.Modifier
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.ui.components.BottomNavItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripnesiaMainScreen() {
    val context = LocalContext.current
    val window = (context as Activity).window

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = Color(0xFF003366).toArgb()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }

    var selectedItemIndex by remember { mutableStateOf(0) }
    val navItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Event", Icons.Filled.CalendarMonth),
        BottomNavItem("Destinasi", Icons.Filled.LocationOn),
        BottomNavItem("Setting", Icons.Filled.Settings)
    )

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
                2 -> DestinationScreen(destinations = DestinationData.destinations)
                3 -> SettingScreen()
            }
        }
    }
}
