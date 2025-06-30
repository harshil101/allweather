package com.harshil.allweather.ui.base

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.harshil.allweather.ui.components.ErrorMessage
import com.harshil.allweather.ui.components.GpsRequestLauncher
import com.harshil.allweather.ui.components.LoadingIndicator
import com.harshil.core.util.getCurrentCityName
import com.harshil.core.util.getCurrentLocation

/**
 * A composable function that displays different UI based on the [UIState].
 *
 * This function handles the common UI states: Initial, Loading, Success, and Error.
 *
 * @param T The type of data associated with the [UIState].
 * @param uiState The current state of the UI.
 * @param modifier The modifier to be applied to the root Box. Defaults to [Modifier].
 * @param onRetry A lambda function to be invoked when the user clicks the retry button in the error state. Defaults to an empty lambda.
 * @param content A composable lambda that defines the UI to be displayed when the [uiState] is [UIState.Success].
 */
@Composable
fun <T> ScreenContent(
    uiState: UIState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    onLocationReceived: (lat: Double, lon: Double, city: String) -> Unit = { _, _, _ -> },
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2196F3)),
        contentAlignment = Alignment.Center
    ) {
        var city by remember { mutableStateOf<String?>("") }
        val context = LocalContext.current
        var location by remember { mutableStateOf<Location?>(null) }
        val permissionGranted = remember { mutableStateOf(false) }
        var launchRequest: ((IntentSenderRequest) -> Unit)? by remember {
            mutableStateOf(
                null
            )
        }


        LocationPermissionRequester(onPermissionGranted = {
            permissionGranted.value = true
        })

        if (permissionGranted.value) {
            GpsRequestLauncher(
                onGpsEnabled = {
                    getCurrentLocation(context) {
                        location = it
                    }
                },
                onFailure = {
                    Toast.makeText(context, "GPS Required", Toast.LENGTH_SHORT).show()
                },
                onLaunchRequest = {
                    launchRequest = it
                }
            )
        }

        location?.let { loc ->
            LaunchedEffect(loc) {
                val (cityName, _) = getCurrentCityName(context, loc)
                city = cityName
                Log.d("Current city", "WeatherSearchScreen: $city")
                onLocationReceived(loc.latitude, loc.longitude, cityName ?: "")
            }
        }
        when (uiState) {
            UIState.Initial -> {
                Text(
                    text = "Getting your location:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            UIState.Loading -> {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UIState.Success -> {
                content()
            }

            is UIState.Error -> {
                ErrorMessage(
                    message = uiState.message,
                    onRetry = onRetry
                )
            }
        }
    }
}
