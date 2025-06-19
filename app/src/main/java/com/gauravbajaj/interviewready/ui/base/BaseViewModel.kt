package com.gauravbajaj.interviewready.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.gauravbajaj.interviewready.ui.base.UIState

abstract class BaseViewModel {
    
    protected val _uiState = MutableStateFlow<UIState<Nothing>>(UIState.Initial)
    val uiState: StateFlow<UIState<Nothing>> = _uiState

}
