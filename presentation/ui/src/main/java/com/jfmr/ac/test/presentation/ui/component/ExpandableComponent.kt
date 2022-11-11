package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean,
    initialVisibility: Boolean,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    onExpanded: @Composable () -> Unit,
) {
    val enter: EnterTransition = remember {
        enterTransition
    }
    val exit = remember {
        exitTransition
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enter,
        exit = exit
    ) {
        onExpanded()
    }
}
