package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.jfmr.ac.test.presentation.ui.character.detail.view.EXPANSTION_TRANSITION_DURATION

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
