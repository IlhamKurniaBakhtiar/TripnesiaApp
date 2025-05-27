package com.tripnesia.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory // Atau ikon lain yang lebih sesuai untuk "Paket"
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripnesia.mobile.R // Penting: Sesuaikan dengan R aplikasi Anda
import com.tripnesia.mobile.ui.theme.TripnesiaAppTheme // Ganti dengan nama tema Anda jika berbeda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripnesiaMainScreen() {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val navItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Event", Icons.Filled.CalendarMonth),
        BottomNavItem("Paket", Icons.Filled.Inventory),
        BottomNavItem("Setting", Icons.Filled.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItemIndex) {
                0 -> HomeScreenContent()
                1 -> EventScreenContent()
                2 -> PackageScreenContent()
                3 -> SettingScreenContent()
            }
        }
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = "Background Pegunungan",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.35f))
            Text(
                text = "Discover Your Journey",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.6f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )
            Spacer(modifier = Modifier.weight(0.65f))
        }
    }
}

@Composable
fun EventScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Halaman Event", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PackageScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Halaman Paket Wisata", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SettingScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Halaman Pengaturan", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}

data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewTripnesiaApp() {
    TripnesiaAppTheme {
        TripnesiaMainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    TripnesiaAppTheme {
        HomeScreenContent()
    }
}