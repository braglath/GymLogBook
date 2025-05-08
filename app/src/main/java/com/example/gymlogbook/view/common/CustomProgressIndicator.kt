package com.example.gymlogbook.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.ui.theme.LightGray

@Composable
fun CustomProgressIndicator() = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(LightGray.copy(alpha = 0.4f)) // semi-transparent background
        .wrapContentSize(Alignment.Center) // Center the progress indicator
) {
    CircularProgressIndicator(
        modifier = Modifier.size(35.dp),
        color = DarkGray
    )
}