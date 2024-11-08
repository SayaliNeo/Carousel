package com.app.carousel.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.app.carousel.presentation.home.CarouselPage
import com.app.carousel.presentation.theme.BlueAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CarouselPage()
                }
            }
        }
    }
}
