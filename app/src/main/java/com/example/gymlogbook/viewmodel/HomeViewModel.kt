package com.example.gymlogbook.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel() {

    val isSignedOut = mutableStateOf(false)

    fun logout() {
        auth.signOut()
        isSignedOut.value = true
    }
}