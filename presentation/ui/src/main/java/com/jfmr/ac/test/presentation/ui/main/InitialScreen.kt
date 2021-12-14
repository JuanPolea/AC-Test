package com.jfmr.ac.test.presentation.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.jfmr.ac.test.presentation.ui.component.MainAppBar
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import com.jfmr.ac.test.presentation.ui.navigation.Navigation

@ExperimentalFoundationApi
@Composable
fun InitialScreen() {
    ThemeAndSurfaceWrapper {
        Scaffold(
            topBar = {
                MainAppBar()
            }
        ) {
            Navigation()
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun ThemeAndSurfaceWrapper(content: @Composable () -> Unit) {
    ACTestTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}