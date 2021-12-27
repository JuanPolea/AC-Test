package com.jfmr.ac.test.presentation.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import com.jfmr.ac.test.presentation.ui.navigation.Navigation

@ExperimentalFoundationApi
@Composable
fun InitialScreen() {
    ThemeAndSurfaceWrapper {
        Scaffold(
            topBar = {
                MainAppBar()
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(cutoutShape = CircleShape) {
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
                }
            }
        ) {
            Navigation()
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