package com.example.gymlogbook.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.gymlogbook.R
import com.example.gymlogbook.services.navigateTo
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.view.DestinationScreen
import com.example.gymlogbook.view.common.CheckSignedOut
import com.example.gymlogbook.view.common.CustomButton
import com.example.gymlogbook.view.common.CustomTopAppBar
import com.example.gymlogbook.viewmodel.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavController, vm: HomeViewModel) {

    CheckSignedOut(navController, vm) // auto logout when variable changes

    // Change the status bar color when this Composable is composed
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = DarkGray,
            darkIcons = false,
        )
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(stringResource(R.string.home)) {
                vm.logout()
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = stringResource(R.string.home))

            CustomButton("Log Weight") {
                navigateTo(navController, DestinationScreen.LogWeight)
            }

        }
    }
}