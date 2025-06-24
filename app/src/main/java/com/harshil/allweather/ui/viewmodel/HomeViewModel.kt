package com.harshil.allweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshil.allweather.data.model.User
import com.harshil.allweather.data.repository.UserRepository
import com.harshil.allweather.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<User>>>(UIState.Initial)
    val uiState: StateFlow<UIState<List<User>>> = _uiState

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                userRepository.getUsers()
                    .collect { users ->
                        _uiState.value = UIState.Success(users)
                    }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Failed to load users")
            }
        }
    }
}
