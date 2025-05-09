package com.example.gymlogbook.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.gymlogbook.R
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.view.common.CheckSignedOut
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
            darkIcons = true,
        )
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(stringResource(R.string.home)) {
                vm.logout()
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = stringResource(R.string.home))

        }
    }
}