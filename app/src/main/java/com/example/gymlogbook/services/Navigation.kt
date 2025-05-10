package com.example.gymlogbook.services

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
    navController.navigate(DestinationScreen.Auth.route) {
        // clears the backstack up to the root.
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true // removed the current screen too
        }
        launchSingleTop = true // avoids multiple instances of the same screen
    }
}

fun navigateToHome(navController: NavController) {
    navController.navigate(DestinationScreen.Home.route) {
        // remove all pages behind by passing 0 to popUpTo
        popUpTo(0)
    }
}
