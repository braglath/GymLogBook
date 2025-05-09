package com.example.gymlogbook.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymlogbook.R
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.view.common.CustomButton
import com.example.gymlogbook.view.common.CustomTopAppBar
import com.example.gymlogbook.view.common.CustomWeightTextField
import com.example.gymlogbook.viewmodel.LogWeightViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LogWeightScreen(navController: NavController, vm: LogWeightViewModel) {
    val focusManager = LocalFocusManager.current

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
            CustomTopAppBar(stringResource(R.string.logWeight)) {
//                vm.logout()
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .imePadding(),
//            .clickable(enabled = true) { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                val weightTextFieldValue = remember { mutableStateOf(TextFieldValue()) }

                Text(
                    "Enter current weight",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                CustomWeightTextField(
                    inputValue = weightTextFieldValue.value,
                    onValueChanged = { weightTextFieldValue.value = it },
                    label = "Weight",
                )

                Spacer(modifier = Modifier.height(50.dp))

                CustomButton(stringResource(R.string.submit)) {
                    if (weightTextFieldValue.value.text.isEmpty()) return@CustomButton
                    val weight = weightTextFieldValue.value.text.toDoubleOrNull()
                    if (weight != null) {
                        focusManager.clearFocus()
                        vm.onSubmitButtonClick(weightTextFieldValue.value.text.toDouble())
                    }
                }
            }

        }

    }
}


