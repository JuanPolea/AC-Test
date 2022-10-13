package com.jfmr.ac.test.presentation.ui.main.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jfmr.ac.test.presentation.ui.R

val Avenir = FontFamily(
    Font(R.font.avenir_roman, FontWeight.Light),
    Font(R.font.avenir_medium, FontWeight.Medium),
    Font(R.font.avenir_black, FontWeight.Black),
    Font(R.font.avenir_book, FontWeight.Normal),
)

val RAMTypography =
    Typography(
        defaultFontFamily = Avenir,
        h1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        ),
        h4 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        subtitle1 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        body1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp,
            lineHeight = 24.sp
        ),
        body2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp,
            lineHeight = 16.sp,
        ),
        button = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp,
        ),
    )
