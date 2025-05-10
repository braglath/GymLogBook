package com.example.gymlogbook.view.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymlogbook.ui.theme.DarkGray

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier.padding(8.dp),
    inputValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        autoCorrectEnabled = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
) =
    OutlinedTextField(
        value = inputValue,
        onValueChange = onValueChanged,
        modifier = modifier,
        label = {
            Text(
                label,
                color = DarkGray,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp),
            )
        },
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = CircleShape,
        keyboardActions = keyboardActions,
    )

@Composable
fun CustomWeightTextField(
    modifier: Modifier = Modifier.padding(8.dp),
    inputValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    label: String,
//    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val decimalRegex = Regex("^\\d{0,5}(\\.\\d{0,2})?$") // Accept up to 2 decimal places

    OutlinedTextField(
        value = inputValue,
        onValueChange = { newValue ->
            if (newValue.text.isEmpty() || decimalRegex.matches(newValue.text)) {
                onValueChanged(newValue)
            }
        },
        modifier = modifier,
        label = {
            Text(
                label,
                color = DarkGray,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp),
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            Text("Kg", color = Color.Gray)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = CircleShape
    )

}