package com.example.gymlogbook.view.common

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.gymlogbook.services.navigateToHome
import com.example.gymlogbook.services.navigateToLogin
import com.example.gymlogbook.viewmodel.AuthViewModel
import com.example.gymlogbook.viewmodel.HomeViewModel

@Composable
fun CheckSignedIn(navController: NavController, vm: AuthViewModel) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = vm.signedIn.value
    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
        navigateToHome(navController)
    }
}

@Composable
fun CheckSignedOut(navController: NavController, vm: HomeViewModel) {
    Log.d("Navigation", "i am here 5")
    val isSignedOut = vm.isSignedOut.value
    if (isSignedOut) {
        Log.d("Navigation", "i am here 6")
        navigateToLogin(navController)
    }
}