package com.harshil.allweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshil.allweather.data.model.WeatherApiResp
import com.harshil.allweather.data.repository.WeatherRepository
import com.harshil.allweather.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeatherApiResp>>(UIState.Initial)
    val uiState: StateFlow<UIState<WeatherApiResp>> = _uiState

    fun fetchCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                weatherRepository.getCurrentWeather(lat, lon)
                    .collect { users ->
                        _uiState.value = UIState.Success(users)
                    }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Failed to load weather")
            }
        }
    }
}
