package com.example.gymlogbook.view

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymlogbook.services.LoadingManager
import com.example.gymlogbook.services.SnackbarManager
import com.example.gymlogbook.services.ToastManager
import com.example.gymlogbook.ui.theme.GymLogBookTheme
import com.example.gymlogbook.view.common.CustomProgressIndicator
import com.example.gymlogbook.view.screens.AuthScreen
import com.example.gymlogbook.view.screens.HomeScreen
import com.example.gymlogbook.viewmodel.AuthViewModel
import com.example.gymlogbook.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class) // for adding TopAppBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymLogBookTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val isLoading = LoadingManager.loadingState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            snackbarHost = {
                // Top-aligned SnackbarHost
                Box(modifier = Modifier.fillMaxWidth()) {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp) // Adjust for status bar
                    )
                }
            },
            //                    snackbarHost = { SnackbarHost(snackbarHostState) },
//                        topBar = {
//                            CustomTopAppBar(stringResource(R.string.app_name)) {
//                                CoroutineScope(Dispatchers.Main).launch {
//                                    ToastManager.showToast("profile image tapped")
//                                }
//                            }
//                        },
//                        bottomBar = {
//                            BottomAppBar { Text("Bottom Bar") }
//                        }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Observe global snackbar messages
                LaunchedEffect(SnackbarManager) {
                    SnackbarManager.snackbarMessages.collect { message ->
                        snackbarHostState.showSnackbar(message)
                    }
                }
                GymLogBookApp()
            }
        }
        // Full-screen Box with CircularProgressIndicator
        if (isLoading.value) CustomProgressIndicator()
    }
}

sealed class DestinationScreen(val route: String) {
    object Auth : DestinationScreen("auth")
    object Home : DestinationScreen("home")
}


@Composable
fun GymLogBookApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // if single view model application, we can have global notification composable here
    // which will get triggered upon the view model variable value changes
    // as this is not, we have used snack bar manager and called in scaffold

    // Observe toast events globally
    LaunchedEffect(Unit) {
        ToastManager.messages.collect { message ->
            val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 100)
            toast.show()
        }
    }

    NavHost(navController, DestinationScreen.Auth.route) {
        composable(DestinationScreen.Auth.route) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            AuthScreen(navController, authViewModel)
        }
        composable(DestinationScreen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController, homeViewModel)
        }
        // for common view model between pages, use format below
//        navigation(
//            startDestination = DestinationScreen.Login.route,
//            route = DestinationScreen.Auth.route
//        ) {
//            // common route for login and signup to share same view model
//            composable(DestinationScreen.Login.route) {
//                val parentEntry = remember(navController.currentBackStackEntry) {
//                    navController.getBackStackEntry(DestinationScreen.Auth.route)
//                }
//                val authViewModel = hiltViewModel<AuthViewModel>(parentEntry)
//                LoginScreen(navController = navController, vm = authViewModel)
//            }
//
//            composable(DestinationScreen.Signup.route) {
//                val parentEntry = remember(navController.currentBackStackEntry) {
//                    navController.getBackStackEntry(DestinationScreen.Auth.route)
//                }
//                val authViewModel = hiltViewModel<AuthViewModel>(parentEntry)
//                SignupScreen(navController = navController, vm = authViewModel)
//            }
//        }
    }
}
