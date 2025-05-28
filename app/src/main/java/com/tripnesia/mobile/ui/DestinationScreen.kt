import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.tripnesia.mobile.data.dummy.DestinationData
import com.tripnesia.mobile.data.model.Destination
import com.tripnesia.mobile.R

@Composable
fun HeaderDestinasi() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        // 1. Gambar background
        Image(
            painter = painterResource(id = R.drawable.kuta),
            contentDescription = "Header Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // 2. Layer hitam transparan
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.4f)) // alpha = 40% transparan
        )

        // 3. Teks paling atas
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
fun DestinationCard(destination: Destination, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = destination.imageRes),
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
                text = destination.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

// Grid untuk menampilkan semua destinasi
@Composable
fun DestinationScreen(destinations: List<Destination>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header tampil penuh tanpa padding
        item {
            HeaderDestinasi()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Rekomendasi wisata terbaik untuk kamu 🍃",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Grid dengan padding horizontal
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) { // padding khusus grid
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
                        DestinationCard(destination = destination)
                    }
                }
            }
        }
    }
}



// Preview dengan data dummy
@Preview(
    showBackground = true,
    name = "Destinasi Full Preview",
    widthDp = 400, // atau 360 sesuai target screen
    heightDp = 800 // cukup besar biar scroll kelihatan
)
@Composable
fun DestinationScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DestinationScreen(destinations = DestinationData.destinations)
        }
    }
}

