package com.jfmr.ac.test.presentation.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import com.jfmr.ac.test.presentation.ui.navigation.AppBottomNavigation
import com.jfmr.ac.test.presentation.ui.navigation.Navigation
import com.jfmr.ac.test.presentation.ui.navigation.navigatePoppingUpToStartDestination

@ExperimentalFoundationApi
@Composable
fun RickAndMortyApp() {
    val appState = rememberAppState()
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    ThemeAndSurfaceWrapper {
        Scaffold(
            topBar = {
                MainAppBar()
            },
            /* floatingActionButton = {
                 FloatingActionButton(onClick = {}) {
                     Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                 }
             },*/
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                AppBottomNavigation(
                    currentRoute = currentRoute,
                    onNavItemClick = {
                        appState.navController.navigatePoppingUpToStartDestination(it.navCommand.route)
                    }
                )
                /* BottomAppBar(cutoutShape = CircleShape) {
                     AppBarIcon(
                         imageVector = Icons.Default.Face,
                         contentDescription = null,
                         action = {}
                     )
                     Spacer(modifier = Modifier.weight(1f))
                     AppBarIcon(
                         Icons.Default.KeyboardArrowUp,
                         contentDescription = stringResource(id = R.string.bottom_bar_left_icon_description),
                         action = {}
                     )
                     AppBarIcon(
                         Icons.Default.KeyboardArrowDown,
                         contentDescription = stringResource(id = R.string.bottom_bar_left_icon_description),
                         action = {}
                     )
                 }*/
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState)
            }
        }
    }
}

@Composable
private fun AppBarIcon(imageVector: ImageVector, contentDescription: String? = "", action: () -> Unit) {
    IconButton(onClick = { action() }) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
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