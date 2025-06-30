package com.harshil.allweather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshil.allweather.ui.base.ScreenContent
import com.harshil.allweather.ui.base.UIState
import com.harshil.allweather.ui.viewmodel.HomeViewModel


/**
 * Composable function for the Home Screen.
 *
 * This screen displays a list of users fetched from a ViewModel.
 * It handles different UI states (Initial, Loading, Success, Error)
 * and allows retrying the data loading on error.
 * Users can click on an item in the list, triggering the [onItemClick] callback.
 *
 * @param modifier The Modifier to be applied to the root Composable of this screen.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text("Weather Report") },
                    colors = TopAppBarColors(
                        Color(0xFF2196F3),
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = Color.Transparent,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            var lat by remember { mutableStateOf<Double?>(null) }
            var lon by remember { mutableStateOf<Double?>(null) }
            var city by remember { mutableStateOf<String?>(null) }
            var hasFetched by remember { mutableStateOf(false) }


            LaunchedEffect(lat, lon) {
                if (lat != null && lon != null  && !hasFetched) {
                    hasFetched = true
                    viewModel.fetchCurrentWeather(lat.toString(), lon.toString())
                }
            }

            ScreenContent(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onLocationReceived = { latitude, longitude, cityName ->
                    lat = latitude
                    lon = longitude
                    city = cityName
                },
                onRetry = {
                    viewModel.fetchCurrentWeather(lat.toString(), lon.toString())
                }
            ) {
                val weatherData = (uiState as? UIState.Success)?.data
                val temp = weatherData?.main?.temp
                WeatherSearchScreen(city ?: "", "" + temp?.toInt())
            }

        }
    }
}

@Composable
fun WeatherSearchScreen(
    city: String = "London",
    temperature: String = "7°",
    hourlyForecast: List<Pair<String, String>> = listOf(
        "1 PM" to "34°",
        "2 PM" to "32°",
        "3 PM" to "31°",
        "4 PM" to "33°"
    ),
    weeklyForecast: List<Pair<String, String>> = listOf(
        "Wed" to "34°",
        "Thu" to "32°",
        "Fri" to "31°",
        "Sat" to "33°"
    ),
    onSearch: (String) -> Unit = {}
) {
    var textValue by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2196F3))
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "$temperature° $city",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                onSearch(it)
                            },
            placeholder = { Text("Search for a city", color = Color.White.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.15f),
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Hourly forecast",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                Color.White.copy(alpha = 0.12f),
                contentColor =  Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hourlyForecast.forEach { (day, temp) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(day, color = Color.White, fontSize = 16.sp)
                        Text(temp, color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
        Text(
            text = "Weekly forecast",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                Color.White.copy(alpha = 0.12f),
                contentColor =  Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weeklyForecast.forEach { (day, temp) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(day, color = Color.White, fontSize = 16.sp)
                        Text(temp, color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
