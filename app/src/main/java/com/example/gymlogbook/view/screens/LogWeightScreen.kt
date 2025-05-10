package com.example.gymlogbook.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymlogbook.R
import com.example.gymlogbook.extensions.StringExtensions.formatToDate
import com.example.gymlogbook.model.WeightData
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.ui.theme.FadedWhite
import com.example.gymlogbook.ui.theme.LightGray
import com.example.gymlogbook.view.common.CustomButton
import com.example.gymlogbook.view.common.CustomTopAppBar
import com.example.gymlogbook.view.common.CustomWeightTextField
import com.example.gymlogbook.view.common.OnlyIconButton
import com.example.gymlogbook.view.common.OnlyTextButton
import com.example.gymlogbook.viewmodel.LogWeightViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Timestamp

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
        containerColor = LightGray,
        topBar = {
            CustomTopAppBar(stringResource(R.string.logWeight)) {
//                vm.logout()
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val halfHeight = this.maxHeight / 2

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(halfHeight)
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = FadedWhite)
                    ) {
                        val weightTextFieldValue = remember { mutableStateOf(TextFieldValue()) }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                "Enter weight",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val date = if (vm.loggedWeightData.value != null) {
                                val timestamp =
                                    vm.loggedWeightData.value?.timestamp
                                if (timestamp == null) {
                                    "Today"
                                } else {
                                    val isToday = vm.isToday(timestamp)
                                    if (isToday) "Today" else timestamp.formatToDate()
                                }
                            } else {
                                "Today"
                            }

                            LoggingDate(date = date.toString())

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                CustomWeightTextField(
                                    inputValue = weightTextFieldValue.value,
                                    onValueChanged = { weightTextFieldValue.value = it },
                                    label = "Weight",
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {

                                    CustomButton("Submit") {
                                        val weight =
                                            weightTextFieldValue.value.text.toDoubleOrNull()
                                        if (weight != null) {
                                            focusManager.clearFocus()
                                            vm.onSubmitButtonClick(weight)
                                        }
                                    }

                                    if (vm.loggedWeightData.value != null && vm.isToday(
                                            vm.loggedWeightData.value?.timestamp ?: Timestamp.now()
                                        ) != true
                                    ) {
                                        OnlyTextButton(label = "Log for Today") {
                                            vm.loggedWeightData.value = null
                                            weightTextFieldValue.value = TextFieldValue()
                                        }
                                    }
                                }
                            }
                        }
                    }


                    // Second Card: Top Half
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(halfHeight)
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = FadedWhite)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                "History",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LazyColumn {
                                items(vm.weightsLog.size) { index ->
                                    val weight = vm.weightsLog[index]
                                    WeightTile(
                                        weight, vm
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoggingDate(date: String) =
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Logging Date:",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            date,
            style = MaterialTheme.typography.bodyLarge
        )
    }


@Composable
fun WeightTile(weight: WeightData, vm: LogWeightViewModel) {
    val isSelected = weight == vm.loggedWeightData.value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                if (isSelected) DarkGray.copy(alpha = 0.5f) else Color.White,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .clickable {
                if (isSelected) {
                    vm.loggedWeightData.value = null
                } else {
                    vm.loggedWeightData.value = weight
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row {


            val date =
                if (weight.timestamp == null) {
                    "Today"
                } else {
                    val isToday = vm.isToday(weight.timestamp)
                    if (isToday) "Today" else weight.timestamp.formatToDate()
                }

            Text(
                text = "${weight.weight} Kg",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = " | ",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        OnlyIconButton(
            icon = Icons.Default.Delete,
            color = if (isSelected) FadedWhite else LightGray
        ) {
            vm.deleteWeightLog(weight)
        }

    }
}