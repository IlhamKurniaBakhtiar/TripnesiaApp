import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tripnesia.mobile.R

// Data class destinasi
data class Destination(
    val title: String,
    val description: String,
    val imageRes: Int
)

// Card untuk satu destinasi
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(destinations) { destination ->
            DestinationCard(destination = destination)
        }
    }
}

// Preview dengan data dummy
@Preview(showBackground = true, name = "destinasi")
@Composable
fun DestinationScreenPreview() {

    val sampleDestinations = listOf(
        Destination("Pantai Kuta", "Pantai terkenal di Bali dengan pasir putih.", R.drawable.kuta),
        Destination("Labuan Bajo", "Gerbang ke Taman Nasional Komodo.", R.drawable.labuan_bajo),
        Destination("Pulau Komodo", "Habitat asli Komodo, warisan dunia.", R.drawable.pulau_komodo),
        Destination("Raja Ampat", "Surga bawah laut yang indah di Papua.", R.drawable.raja_ampat),
        Destination("Pantai Kuta", "Pantai terkenal di Bali dengan pasir putih.", R.drawable.kuta),
        Destination("Labuan Bajo", "Gerbang ke Taman Nasional Komodo.", R.drawable.labuan_bajo),
        Destination("Pulau Komodo", "Habitat asli Komodo, warisan dunia.", R.drawable.pulau_komodo),
        Destination("Raja Ampat", "Surga bawah laut yang indah di Papua.", R.drawable.raja_ampat),
        Destination("Pantai Kuta", "Pantai terkenal di Bali dengan pasir putih.", R.drawable.kuta),
        Destination("Labuan Bajo", "Gerbang ke Taman Nasional Komodo.", R.drawable.labuan_bajo),
        Destination("Pulau Komodo", "Habitat asli Komodo, warisan dunia.", R.drawable.pulau_komodo),
        Destination("Raja Ampat", "Surga bawah laut yang indah di Papua.", R.drawable.raja_ampat),
    )

    MaterialTheme {
        DestinationScreen(destinations = sampleDestinations)
    }
}
