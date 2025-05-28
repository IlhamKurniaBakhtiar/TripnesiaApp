package com.tripnesia.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripnesia.mobile.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun EventScreen(modifier: Modifier = Modifier) {
    // DATA DUMMY
    val sampleEvents = listOf(
        Event(
            id = 1,
            title = "Pesta Kesenian Bali",
            date = "21 Juni - 19 Juli 2025",
            kategori = "Festival Budaya",
            imageResId = R.drawable.pesta_bali,
            description = "Pesta Kesenian Bali adalah festival tahunan yang merayakan kekayaan budaya dan kesenian provinsi Bali. Festival ini dibuka dengan pelepasan pawai budaya kemudian disusul dengan pergelaran pada malam harinya di Panggung Terbuka Ardha Candra, Art Center Denpasar. Berbagai pertunjukan seni diselenggarakan selama sebulan di Art Center Denpasar. Pesta Kesenian Bali tahun 2025 ini dirangkai dengan lomba, lokakarya, serasehan, parade, dan penghargaan pengabdi seni yang kesemuanya mengusung tema kesenian dan kebudayaan Bali. Selain itu, PKB tahun 2025 dirangkai dengan kegiatan Jantra Tradisi Bali dan BWCC (Perayaan Kebudayaan Dunia di Bali)."
        ),
        Event(
            id = 2,
            title = "Jember Fashion Carnaval",
            date = "8 - 10 Agustus 2025",
            kategori = "Fashion & Parade",
            imageResId = R.drawable.karnaval_jember,
            description = "Jember Fashion Carnaval merupakan event sosial dengan konsep fesyen kreatif yang dikemas dalam bentuk parade karnaval. Festival ini adalah agenda tahunan untuk menampilkan berbagai busana unik yang mewakili filosofi dan budaya dari berbagai wilayah Nusantara. Terdapat juga pasar UMKM yang memamerkan berbagai produk dan layanan inovatif untuk pengunjung."
        ),
        Event(
            id = 3,
            title = "Wayang Jogja Night Carnival",
            date = "28 - 31 Mei 2025",
            kategori = "Seni & Teater Jalanan",
            imageResId = R.drawable.wayang_jogja,
            description = "Acara ini termasuk dalam event unggulan kota Yogyakarta yang diadakan dalam memperingati HUT Kota Yogyakarta. WJNC menyuguhkan karnaval jalanan art (art on the street) yang menggabungkan tema pewayangan dengan melibatkan seni koreografi, busana, musik kontemporer, dan permainan lighting. Acara ini sudah dilaksanakan secara rutin sejak 2016 dan telah berkolaborasi dengan komunitas seni, UMKM, dan dinas terkait dalam memberikan pengalaman terbaik untuk pengunjung."
        ),
        Event(
            id = 4,
            title = "Festival Payung Indonesia",
            date = "5 - 7 September 2025",
            kategori = "Seni Instalasi & Kuliner",
            imageResId = R.drawable.festival_payung,
            description = "Festival Payung Indonesia menghadirkan karya dan kreasi seni dengan payung  berupa seni tari, musik, dan fashion show yang bertujuan memberikan inspirasi bagi masyarakat. Festival ini juga menyuguhkan pasar kuliner dan pameran UMKM. Banyak seniman dari berbagai kota di Indonesia berpartisipasi ikut dalam festival ini. Festival Payung Indonesia sudah menjadi acara tahunan di Solo sejak tahun 2014."
        ),
        Event(
            id = 5,
            title = "Cap Go Meh Kota Singkawang",
            date = "2 Januari - 3 Februari 2025",
            kategori = "Perayaan Imlek",
            imageResId = R.drawable.cap_go_meh,
            description = "Kota Singkawang menyuguhkan berbgaai kegiatan dalam menyambut perayaan Imlek dan Cap Go Meh dengan menghiasi kota dengan lampion. Terdapat juga banyak rangkaian kegiatan lain seperti lomba hias lingkungan, opening ceremony, pentas pagelaran seni dan expo, replika shio, festival kuliner, pawai lampion, ritual tolak bala, dan berbagai acara meriah lainnya. Festival ini dapat menjadi pilihan liburan yang dapat dikunjungi."
        ),
        Event(
            id = 6,
            title = "Semasa Piknik",
            date = "27 - 29 Juni 2025",
            kategori = "UMKM & Outdoor",
            imageResId = R.drawable.semasa_piknik,
            description = "Semasa Piknik ialah event tahunan yang digelar oleh Semasa untuk mengajak warga Jakarta menndukung ekonomi kreatif dan UMKM lokal sambil menikmati kegiatan outdoor di kota Jakarta dalam bentuk piknik bersama. "
        ),
        Event(
            id = 7,
            title = "Pesona Belitung Beach Festival",
            date = "8 - 12 Mei 2025",
            kategori = "Seni Pesisir & Kuliner",
            imageResId = R.drawable.festival_belitung,
            description = "Acara ini adalah perayaan yang menyuguhkan keberagaman seni dan budaya pesisir pulau Belitung dengan menampilkan berbagai pertunjukan seni tradisional dan modern. Festival ini juga menampikan pameran produk ekonomi kreatif, kuliner, serta berbagai lomba bertemakan kebaharian. Tujuan dari festival ini adalah memperkenalkan ke khalayak keberagaman seni dan budaya masyarakat Belitung, dan didukung keindahakan alam dan tradisi pesisir."
        ),
        Event(
            id = 8,
            title = "Festival Rakik-Rakik",
            date = "28 - 30 Juni 2025",
            kategori = "Tradisi Lokal",
            imageResId = R.drawable.festival_rakik,
            description = "Festival Rakik-Rakik adalah acara tahunan dalam berlomba menghias rakit yang dilakukan oleh masyakat di Nagari Maninjau dalam merayakan tradisi dan menyambut hari raya idul fitri. Festival ini menjadi wadah untuk menuangkan kreativitas dan memperkuat semangat kebersamaan. Peserta dari berbagai jorong dan nagari menunjukkan kebanggaan budaya dan warisan mereka. Festival ini juga menghadirkan pengalaman yang intens tentang budaya Minangkabau, dengan pertunjukkan seni tradisional, hidangan khas lokal, dan kemeriahan acara."
        ),
        Event(
            id = 9,
            title = "Festival Lampion Waisak",
            date = "12 Mei 2025",
            kategori = "Perayaan Agama & Spiritualitas",
            imageResId = R.drawable.festival_lampion,
            description = "Festival Lampion Waisak diselenggarakan di Candi Borodudur dengan mengangkat tema “Light of Peace” sebagai simbol perdamaian dunia. Acara ini bisa diikuti oleh semua kalangan masyarakat dengan bersama-sama menerbangkan lampion di depan Candi Borobudur dengan latar malam sehingga menambah pengalaman spiritual dalam merayakan  Waisak dengan suasana penuh kedamaian. Untuk dapat mengikuti acara ini, pengunjung perlu memesan terlebih dahulu tiket dari jauh-jauh hari."
        )
    )

    // STATE untuk pindah halaman detail
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    if (selectedEvent == null) {
        // Tampilan daftar event
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Events",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(sampleEvents) { event ->
                    EventItem(event = event) {
                        selectedEvent = event
                    }
                }

                // Tambahan info
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Acara lainnya akan diupdate segera.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    )
                }
            }

        }
    } else {
        // Tampilan detail
        EventDetailScreen(event = selectedEvent!!) {
            selectedEvent = null
        }
    }
}

// Data class
data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val kategori: String,
    val imageResId: Int,
    val description: String
)

// Kartu item event
@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = event.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "${event.date} • ${event.kategori}",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

        }
    }
}

// Halaman detail event
@Composable
fun EventDetailScreen(event: Event, onBack: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // ⬅️ scroll ditambahkan di sini
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = event.imageResId),
            contentDescription = event.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(event.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("${event.date} | ${event.kategori}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = event.description,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}