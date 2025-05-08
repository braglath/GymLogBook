package com.example.gymlogbook.view.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.gymlogbook.services.navigateToHome
import com.example.gymlogbook.viewmodel.AuthViewModel

@Composable
fun CheckSignedIn(navController: NavController, vm: AuthViewModel) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = vm.signedIn.value
    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
        navigateToHome(navController)
    }
}