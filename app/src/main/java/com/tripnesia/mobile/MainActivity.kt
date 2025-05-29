package com.tripnesia.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import com.tripnesia.mobile.ui.TripnesiaMainScreen
import com.tripnesia.mobile.ui.SplashScreen
import com.tripnesia.mobile.ui.theme.TripnesiaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TripnesiaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isSplashScreenVisible by remember { mutableStateOf(true) }

                    if (isSplashScreenVisible) {
                        SplashScreen(onAnimationEnd = {
                            isSplashScreenVisible = false
                        })
                    } else {
                        TripnesiaMainScreen()
                    }
                }
            }
        }
    }
}
