package com.gauravbajaj.interviewready.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gauravbajaj.interviewready.ui.components.ErrorMessage

@Composable
fun <T> ScreenContent(
    uiState: UIState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            UIState.Initial -> {
                // Initial state UI (placeholder or empty view)
            }

            UIState.Loading -> {
                CircularProgressIndicator()
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
