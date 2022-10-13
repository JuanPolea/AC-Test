package com.jfmr.ac.test.presentation.ui.main.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun ACTestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }
    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
