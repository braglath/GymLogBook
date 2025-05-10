package com.example.gymlogbook.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gymlogbook.R

val funnelDisplayFontFamily = FontFamily(
    Font(R.font.funneldisplay_regular, FontWeight.Normal),
    Font(R.font.funneldisplay_light, FontWeight.Medium),
    Font(R.font.funneldisplay_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = funnelDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = DarkGray
    ),
    bodyLarge = TextStyle(
        fontFamily = funnelDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = DarkGray
    ),
//    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = funnelDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = DarkGray
    ),
    labelSmall = TextStyle(
        fontFamily = funnelDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = DarkGray
    )
//    */
)