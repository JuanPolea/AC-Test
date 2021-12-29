package com.jfmr.ac.test.presentation.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import com.jfmr.ac.test.presentation.ui.navigation.AppBottomNavigation
import com.jfmr.ac.test.presentation.ui.navigation.Navigation

@ExperimentalFoundationApi
@Composable
fun RickAndMortyApp() {
    val appState = rememberAppState()
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    ThemeAndSurfaceWrapper {
        Scaffold(
            topBar = {
                MainAppBar()
            },
            bottomBar = {
                AppBottomNavigation(
                    currentRoute = appState.currentRoute,
                    onNavItemClick = {
                        appState.onNavItemClick(it.navCommand.route)
                    }
                )
            },
            scaffoldState = appState.scaffoldState
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState)
            }
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
