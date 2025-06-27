package com.tripnesia.mobile.ui.screen.paket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tripnesia.mobile.data.dummy.TravelPackageData
import com.tripnesia.mobile.data.model.TravelPackage
import com.tripnesia.mobile.R
import com.tripnesia.mobile.ui.NavigationDestination
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.tripnesia.mobile.viewmodel.PackageViewModel


@Composable
fun HeaderPackage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kuta), // ganti dengan yang kamu punya
            contentDescription = "Header Paket",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Eksplorasi Alam Nusantara",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Petualangan di pegunungan, laut, dan sungai",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PackageGridCard(
    travelPackage: TravelPackage,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageResId = remember(travelPackage.imageUrl) {
        context.resources.getIdentifier(travelPackage.imageUrl, "drawable", context.packageName)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = travelPackage.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = travelPackage.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = travelPackage.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp${travelPackage.price}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


@Composable
fun PackageScreen(
    onPackageClick: (TravelPackage) -> Unit,
    viewModel: PackageViewModel = viewModel()
) {
    val packages by viewModel.packages.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🔹 Header Composable
        item {
            HeaderPackage()
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // 🔹 Judul rekomendasi
        item {
            Text(
                text = "Rekomendasi paket wisata alam untuk kamu",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Start
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // 🔹 Grid Card
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 10000.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(packages) { travelPackage ->
                        PackageGridCard(
                            travelPackage = travelPackage,
                            onClick = { onPackageClick(travelPackage) }
                        )
                    }
                }
            }
        }
    }
}
