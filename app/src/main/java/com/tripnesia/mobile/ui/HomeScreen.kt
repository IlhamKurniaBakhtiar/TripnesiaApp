package com.tripnesia.mobile.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tripnesia.mobile.R

data class TeamMember(
    val name: String,
    val role: String,
    @DrawableRes val imageRes: Int,
    val linkedinUrl: String?,
    val githubUrl: String?
)

val teamMembers = listOf(
    TeamMember(
        name = "Ilham Kurnia Bakhtiar",
        role = "Project Manager",
        imageRes = R.drawable.ilham_kurnia,
        linkedinUrl = "https://www.linkedin.com/in/ilham-kurnia-bakhtiar-710a40326/",
        githubUrl = "https://github.com/IlhamKurniaBakhtiar"
    ),
    TeamMember(
        name = "Robby Septian Fajar",
        role = "Programmer",
        imageRes = R.drawable.robby_septian,
        linkedinUrl = "https://www.linkedin.com/in/robby-septian-fajar-2b7b4b253/",
        githubUrl = "https://github.com/robby-sf"
    ),
    TeamMember(
        name = "Shofi Zahrotul Aulia",
        role = "Requirement Analyst",
        imageRes = R.drawable.shofi_zahrotul,
        linkedinUrl = "https://www.linkedin.com/in/shofi-zahrotul-aulia-870555320",
        githubUrl = "https://github.com/shfzhlaa"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val isButtonVisible by remember {
        derivedStateOf { scrollState.value < 200 }
    }

    Box(modifier = modifier.fillMaxSize()) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_image),
                    contentDescription = "Background Pegunungan",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Discover Your Journey",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Tripnesia adalah platform pariwisata terbesar di Indonesia",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tripnesia merupakan sebuah platform informasi sekaligus perjalanan yang menyediakan berbagai destinasi wisata alam yang ada di Indonesia.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kami percaya bahwa keindahan alam Indonesia ada untuk dinikmati seluruh orang, melalui platform Tripnesia kami menyediakan berbagai informasi mengenai berbagai destinasi untuk semua orang.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Meet Our Team",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kami adalah tim profesional yang berdedikasi untuk memberikan yang terbaik.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Top
                ) {
                    teamMembers.forEach { member ->
                        TeamMemberCard(member = member)
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isButtonVisible,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable {
                        val phoneNumber = "62895391671188"
                        val url = "https://wa.me/$phoneNumber"
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast
                                .makeText(
                                    context,
                                    "Tidak ada aplikasi yang bisa membuka link ini.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.SupportAgent,
                    contentDescription = "Ikon Customer Service",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hubungi CS",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TeamMemberCard(member: TeamMember, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .width(110.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = member.imageRes),
                contentDescription = "Foto ${member.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = member.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2
            )
            Text(
                text = member.role,
                fontSize = 10.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!member.linkedinUrl.isNullOrBlank()) {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(member.linkedinUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo_linkedin),
                            contentDescription = "LinkedIn",
                            tint = Color.Unspecified
                        )
                    }
                }

                if (!member.githubUrl.isNullOrBlank()) {
                    if(!member.linkedinUrl.isNullOrBlank()) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(member.githubUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo_github),
                            contentDescription = "GitHub",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}