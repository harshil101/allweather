package com.gauravbajaj.interviewready

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gauravbajaj.interviewready.navigation.InterviewReadyNavHost
import com.gauravbajaj.interviewready.ui.theme.ExperimentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentTheme {
                InterviewReadyNavHost()
            }
        }
    }
}

@Composable
fun InterviewReadyApp() {
    InterviewReadyNavHost()
}

@Composable
fun InterviewReadyPreview() {
    ExperimentTheme {
        InterviewReadyApp()
    }
}