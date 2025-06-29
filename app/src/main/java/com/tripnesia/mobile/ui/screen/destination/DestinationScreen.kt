package com.tripnesia.mobile.ui.screen.destination

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
import com.tripnesia.mobile.data.model.Destination
import com.tripnesia.mobile.R
import com.tripnesia.mobile.viewmodel.DestinationViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


@Composable
fun HeaderDestinasi() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kuta),
            contentDescription = "Header Background",
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
                text = "Temukan Surga Tersembunyi di Indonesia",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Dari pegunungan sejuk hingga laut tropis yang jernih",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DestinationCard(
    destination: Destination,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageResId = remember(destination.imageUrl) {
        context.resources.getIdentifier(destination.imageUrl, "drawable", context.packageName)
    }

    val imagePainter = if (imageResId != 0) {
        painterResource(id = imageResId)
    } else {
        painterResource(id = R.drawable.raja_ampat)
    }
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = imagePainter,
                contentDescription = destination.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = destination.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = if (destination.description.length > 50)
                    destination.description.take(50) + "..."
                    else
                        destination.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun DestinationScreen(
    onDestinationClick: (Destination) -> Unit,
    viewModel: DestinationViewModel = viewModel()
) {
    val destinations by viewModel.destinations.collectAsState()


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            HeaderDestinasi()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Rekomendasi wisata terbaik untuk kamu",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

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
                    items(destinations) { destination ->
                        DestinationCard(
                            destination = destination,
                            onClick = { onDestinationClick(destination) }
                        )

                    }
                }
            }
        }
    }
}



@Preview(
    showBackground = true,
    name = "Destinasi Full Preview",
    widthDp = 400,
    heightDp = 800
)
@Composable
fun DestinationScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        NavigationDestination(navController = navController)
    }
}

