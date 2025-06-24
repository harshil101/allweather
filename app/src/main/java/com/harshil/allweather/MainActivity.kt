package com.harshil.allweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.harshil.allweather.navigation.AllWeatherNavHost
import com.harshil.allweather.ui.theme.AllWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllWeatherTheme {
                AllWeatherNavHost()
            }
        }
    }
}

@Composable
fun AllWeatherApp() {
    AllWeatherNavHost()
}

@Composable
fun AllWeatherAppPreview() {
    AllWeatherTheme {
        AllWeatherApp()
    }
}