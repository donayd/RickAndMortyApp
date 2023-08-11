package com.zara.rickandmorty.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.AirlineSeatFlat
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.zara.rickandmorty.ui.theme.gray
import com.zara.rickandmorty.ui.theme.green
import com.zara.rickandmorty.ui.theme.red

fun parseStatusToColor(gender: String): Color {
    return when (gender) {
        "Alive" -> green
        "Dead" -> red
        else -> gray
    }
}

fun parseStatusToIcon(gender: String): ImageVector {
    return when (gender) {
        "Alive" -> Icons.Default.AccessibilityNew
        "Dead" -> Icons.Default.AirlineSeatFlat
        else -> Icons.Default.QuestionMark
    }
}