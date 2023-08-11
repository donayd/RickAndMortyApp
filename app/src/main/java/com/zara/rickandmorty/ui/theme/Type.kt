package com.zara.rickandmorty.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zara.rickandmorty.R

val Schwifty = FontFamily(
    Font(R.font.get_schwifty, FontWeight.Normal)
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = Schwifty,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)