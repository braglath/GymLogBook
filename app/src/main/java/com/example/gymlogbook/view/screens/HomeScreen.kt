package com.example.gymlogbook.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gymlogbook.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, vm: HomeViewModel) {
    Text("Home Screen")
}