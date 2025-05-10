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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
                    LogHeightCard(height = halfHeight, vm = vm, focusManager = focusManager)
                    HistoryCard(height = halfHeight, vm = vm)
                }
            }
        }
    }
}

@Composable
fun LogHeightCard(height: Dp = 200.dp, vm: LogWeightViewModel, focusManager: FocusManager) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = FadedWhite)
    ) {
        val todayString = stringResource(R.string.today)
        val date = if (vm.loggedWeightData.value != null) {
            val timestamp =
                vm.loggedWeightData.value?.timestamp
            if (timestamp == null) {
                todayString
            } else {
                val isToday = vm.isToday(timestamp)
                if (isToday) todayString else timestamp.formatToDate()
            }
        } else {
            todayString
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val enterWeightString = stringResource(R.string.enterWeight)
            Text(
                enterWeightString,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))


            LoggingDate(date = date.toString())

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val weightTextFieldValue = remember { mutableStateOf(TextFieldValue()) }
                val weightString = stringResource(R.string.weight)

                CustomWeightTextField(
                    inputValue = weightTextFieldValue.value,
                    onValueChanged = { weightTextFieldValue.value = it },
                    label = weightString,
                )

                Spacer(modifier = Modifier.height(16.dp))
                SubmitLogTodayButtons(
                    weightTextFieldValue = weightTextFieldValue,
                    focusManager = focusManager,
                    vm = vm
                )

            }
        }
    }
}

@Composable
fun SubmitLogTodayButtons(
    weightTextFieldValue: MutableState<TextFieldValue>,
    focusManager: FocusManager,
    vm: LogWeightViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val submitString = stringResource(R.string.submit)

        CustomButton(submitString) {
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
            val logForTodayString = stringResource(R.string.logForToday)

            OnlyTextButton(label = logForTodayString) {
                vm.loggedWeightData.value = null
                weightTextFieldValue.value = TextFieldValue()
            }
        }
    }
}


@Composable
fun HistoryCard(height: Dp = 200.dp, vm: LogWeightViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
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
            val historyString = stringResource(R.string.history)

            Text(
                historyString,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeightList(vm)
        }
    }
}

@Composable
fun WeightList(vm: LogWeightViewModel) {
    LazyColumn {
        items(vm.weightsLog.size) { index ->
            val weight = vm.weightsLog[index]
            val isSelected = weight == vm.loggedWeightData.value
            val todayString = stringResource(R.string.today)
            val date =
                if (weight.timestamp == null) {
                    todayString
                } else {
                    val isToday = vm.isToday(weight.timestamp)
                    if (isToday) todayString else weight.timestamp.formatToDate()
                }
            WeightTile(
                weight = weight, isSelected = isSelected,
                date = date,
                onTileClick = {
                    if (isSelected) {
                        vm.loggedWeightData.value = null
                    } else {
                        vm.loggedWeightData.value = weight
                    }
                }, onDeleteClick = {
                    vm.deleteWeightLog(weight)
                }
            )
        }
    }
}

@Preview(name = "LoggingDate", group = "LogWeightScreen")
@Composable
fun LoggingDate(date: String = "Today") =
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val loggingDateString = stringResource(R.string.loggingDate)
        Text(
            "${loggingDateString}:",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            date,
            style = MaterialTheme.typography.bodyLarge
        )
    }

@Preview(name = "WeightTile", group = "LogWeightScreen")
@Composable
fun PreviewWeightTile() {
    val isSelected = false
    val weight = WeightData()
    MaterialTheme {
        WeightTile(
            weight = weight, isSelected = isSelected,
            date = "Today",
            onTileClick = {},
            onDeleteClick = {}
        )
    }
}

@Composable
fun WeightTile(
    weight: WeightData,
    date: String,
    isSelected: Boolean = false,
    onTileClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {

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
                onTileClick.invoke()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row {

            val kgString = stringResource(R.string.kg)

            Text(
                text = "${weight.weight} $kgString",
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
            onDeleteClick.invoke()
        }

    }
}