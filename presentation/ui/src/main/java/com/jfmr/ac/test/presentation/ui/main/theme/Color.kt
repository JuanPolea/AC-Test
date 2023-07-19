package com.jfmr.ac.test.presentation.ui.main.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val md_theme_light_primary = Color(0xFF00d25a)
private val md_theme_light_onPrimary = Color(0xFFEBE540)
private val md_theme_light_primaryContainer = Color(0xFFE4F9E8)
private val md_theme_light_onPrimaryContainer = Color(0xFFA9F5B7)

private val md_theme_dark_onPrimary = Color(0xFFed8be0)
private val md_theme_dark_primary = Color(0xFFdd00ca)
private val md_theme_dark_primaryContainer = Color(0xFF313131)
private val md_theme_dark_onPrimaryContainer = Color(0xFFDBC8C8)

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
)
val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
)
