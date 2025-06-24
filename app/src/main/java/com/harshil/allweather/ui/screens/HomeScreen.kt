package com.harshil.allweather.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.harshil.allweather.data.model.User
import com.harshil.allweather.ui.viewmodel.HomeViewModel
import com.harshil.allweather.ui.base.ScreenContent
import com.harshil.allweather.ui.base.UIState

/**
 * Composable function for the Home Screen.
 *
 * This screen displays a list of users fetched from a ViewModel.
 * It handles different UI states (Initial, Loading, Success, Error)
 * and allows retrying the data loading on error.
 * Users can click on an item in the list, triggering the [onItemClick] callback.
 *
 * @param navController The NavController used for navigation.
 * @param modifier The Modifier to be applied to the root Composable of this screen.
 * @param onItemClick A callback function that is invoked when a user item is clicked.
 *                    It receives the clicked [User] object as a parameter.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (User) -> Unit = {}
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        if (uiState is UIState.Initial) {
            viewModel.loadUsers()
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Interview Ready") }
            )
        }
    ) { paddingValues ->
        ScreenContent(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onRetry = {
                viewModel.loadUsers()
            }
        ) {

            val successState = uiState as UIState.Success

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(successState.data) { user ->
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(user) }
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}
