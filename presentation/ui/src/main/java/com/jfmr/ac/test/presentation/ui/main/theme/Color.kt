package com.jfmr.ac.test.presentation.ui.main.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val primaryColor = Color(0xFF00d25a)
private val primaryVariantColor = Color(0xFF00b82c)
private val secondaryColor = Color(0xFFEBE540)
private val background = Color(0xFFE4F9E8)

private val primaryDarkColor = Color(0xFFdd00ca)
private val primaryVariantDarkColor = primaryColor
private val secondaryDarkColor = Color(0xFFed8be0)

val LightColors = lightColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    background = background
)
val DarkColors = darkColorScheme(
    primary = primaryDarkColor,
    secondary = secondaryDarkColor,
)
