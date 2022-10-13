package com.jfmr.ac.test.presentation.ui.main.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val primaryColor = Color(0xFF00d25a)
private val primaryVariantColor = Color(0xFF00b82c)
private val secondaryColor = Color(0xFFEBE540)
private val background = Color(0xFFE4F9E8)

private val primaryDarkColor = Color(0xFFdd00ca)
private val primaryVariantDarkColor = primaryColor
private val secondaryDarkColor = Color(0xFFed8be0)

val LightColors = lightColors(
    primary = primaryColor,
    primaryVariant = primaryVariantColor,
    secondary = secondaryColor,
    background = background,
)

val DarkColors = darkColors(
    primary = primaryDarkColor,
    primaryVariant = primaryVariantDarkColor,
    secondary = secondaryDarkColor,
)
