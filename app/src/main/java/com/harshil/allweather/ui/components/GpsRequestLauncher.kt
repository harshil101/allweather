package com.harshil.allweather.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

@Composable
fun GpsRequestLauncher(
    onGpsEnabled: () -> Unit,
    onFailure: () -> Unit,
    onLaunchRequest: ((IntentSenderRequest) -> Unit) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onGpsEnabled()
        } else {
            onFailure()
        }
    }

    // Expose a way to launch GPS dialog
    LaunchedEffect(Unit) {
        if (activity != null) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)

            val settingsClient = LocationServices.getSettingsClient(context)

            val task = settingsClient.checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                // GPS is already enabled
                onGpsEnabled()
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    launcher.launch(intentSenderRequest)
                } else {
                    Toast.makeText(context, "GPS not available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
