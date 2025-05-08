package com.example.gymlogbook.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymlogbook.extensions.StringExtensions.isValidEmail
import com.example.gymlogbook.extensions.StringExtensions.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object Helper {
    fun ViewModel.showToast(message: String) {
        this.viewModelScope.launch {
            ToastManager.showToast(message)
        }
    }

    fun ViewModel.showSnackbar(message: String) {
        this.viewModelScope.launch {
            SnackbarManager.showSnackbar(message)
        }
    }
}

object LoadingManager {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    fun showLoading() {
        _loadingState.value = true
    }

    fun hideLoading() {
        _loadingState.value = false
    }
}