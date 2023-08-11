package com.zara.rickandmorty.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.zara.rickandmorty.ui.theme.blue
import com.zara.rickandmorty.ui.theme.gray
import com.zara.rickandmorty.ui.theme.pink

fun parseGenderToColor(gender: String): Color {
    return when (gender) {
        "Male" -> blue
        "Female" -> pink
        else -> gray
    }
}

fun parseGenderToIcon(gender: String): ImageVector {
    return when (gender) {
        "Male" -> Icons.Default.Male
        "Female" -> Icons.Default.Female
        else -> Icons.Default.QuestionMark
    }
}