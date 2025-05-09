package com.example.gymlogbook.services

import android.util.Log
import androidx.navigation.NavController
import com.example.gymlogbook.model.NavParam
import com.example.gymlogbook.view.DestinationScreen


fun navigateTo(navController: NavController, dest: DestinationScreen, vararg params: NavParam) {
    val currentEntry = navController.currentBackStackEntry
    val handle = currentEntry?.savedStateHandle

    for (param in params) {
        handle?.set(param.name, param.value)
    }

    navController.navigate(dest.route) {
        popUpTo(dest.route)
        launchSingleTop = true
    }
}

fun navigateToLogin(navController: NavController) {
    Log.d("Navigation", "i am here 1")
    navController.navigate(DestinationScreen.Auth.route) {
        // clears the backstack up to the root.
        Log.d("Navigation", "i am here 2")
        popUpTo(navController.graph.startDestinationId) {
            Log.d("Navigation", "i am here 3")
            inclusive = true // removed the current screen too
        }
        Log.d("Navigation", "i am here 4")
        launchSingleTop = true // avoids multiple instances of the same screen
    }
}

fun navigateToHome(navController: NavController) {
    navController.navigate(DestinationScreen.Home.route) {
        // remove all pages behind by passing 0 to popUpTo
        popUpTo(0)
    }
}
