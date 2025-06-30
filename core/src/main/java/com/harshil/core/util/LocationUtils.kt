package com.harshil.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}


fun openGpsSettings(context: Context) {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    context.startActivity(intent)
}


@SuppressLint("MissingPermission") // We're checking permission before calling this
fun getCurrentLocation(context: Context, onLocationFound: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                Log.d("Location", "Last location: ${location.latitude}, ${location.longitude}")
                onLocationFound(location)
            } else {
                // Force location update
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 1000
                    numUpdates = 1
                }

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        fusedLocationClient.removeLocationUpdates(this)
                        val loc = result.lastLocation
                        Log.d("Location", "Fresh location: ${loc?.latitude}, ${loc?.longitude}")
                        onLocationFound(loc)
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }
        .addOnFailureListener {
            Log.e("Location", "Failed to get location: ${it.message}")
            onLocationFound(null)
        }
}

// Function to get current city name
suspend fun getCurrentCityName(context: Context, location: Location): Pair<String?, String?> {
    return withContext(Dispatchers.IO) {
        try {
            run {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val city = addresses?.firstOrNull()?.locality
                if (!city.isNullOrEmpty()) {
                    Pair(city, null)
                } else {
                    Pair(null, "City not found.")
                }
            }
        } catch (e: Exception) {
            Pair(null, "Error: ${e.message}")
        }
    }
}
