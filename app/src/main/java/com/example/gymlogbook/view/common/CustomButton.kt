package com.example.gymlogbook.view.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.ui.theme.FadedWhite
import com.example.gymlogbook.ui.theme.LightGray

@Composable
fun CustomButton(label: String, onClick: () -> Unit) = OutlinedButton(
    onClick = onClick,
    modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        .fillMaxWidth(0.5f),
    colors = ButtonDefaults.buttonColors(Color.Transparent),
    elevation = ButtonDefaults.buttonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
    shape = RoundedCornerShape(100)
) {
    Text(label, color = Color.Black)
}
@Composable
fun CustomIconButton(icon: ImageVector, onClick: () -> Unit) = OutlinedButton(
    onClick = onClick,
    modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        .fillMaxWidth(0.5f),
    colors = ButtonDefaults.buttonColors(Color.Transparent),
    elevation = ButtonDefaults.buttonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
    shape = RoundedCornerShape(100)
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = DarkGray
    )
}

@Composable
fun OnlyIconButton(icon: ImageVector,  color: Color, onClick: () -> Unit){
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(0.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = color
        ),
        shape = RoundedCornerShape(100)
    ) {
        Icon(imageVector =icon, contentDescription = null)
    }
}

@Composable
fun OnlyTextButton(label: String,  color: Color = Color.Blue, onClick: () -> Unit){
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(0.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = color
        ),
        shape = RoundedCornerShape(100)
    ) {
        Text(label)
    }
}