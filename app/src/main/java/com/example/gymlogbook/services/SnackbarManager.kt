package com.example.gymlogbook.services

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarManager {
    private val _snackbarMessages = MutableSharedFlow<String>()
    val snackbarMessages = _snackbarMessages.asSharedFlow()

    suspend fun showSnackbar(message: String) {
        _snackbarMessages.emit(message)
    }
}