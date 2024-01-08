package com.zara.rickandmorty.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.AirlineSeatFlat
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.zara.rickandmorty.ui.theme.blue
import com.zara.rickandmorty.ui.theme.gray
import com.zara.rickandmorty.ui.theme.green
import com.zara.rickandmorty.ui.theme.red

fun parseSpeciesToColor(species: String): Color {
    return when (species) {
        "Human" -> blue
        "Robot" -> gray
        "Alien" -> green
        "Animal" -> green
        else -> gray
    }
}

fun parseSpeciesToIcon(species: String): ImageVector {
    return when (species) {
        "Human" -> Icons.Default.Person
        "Robot" -> Icons.Default.SmartToy
        "Alien" -> Icons.Default.Android
        "Animal" -> Icons.Default.CrueltyFree
        else -> Icons.Default.QuestionMark
    }
}