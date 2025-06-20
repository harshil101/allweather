package com.gauravbajaj.interviewready.ui.screens

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
import com.gauravbajaj.interviewready.data.model.User
import com.gauravbajaj.interviewready.ui.viewmodel.HomeViewModel
import com.gauravbajaj.interviewready.ui.base.ScreenContent
import com.gauravbajaj.interviewready.ui.base.UIState

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
