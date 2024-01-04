package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.main.theme.NoRippleTheme
import kotlinx.coroutines.launch


@Composable
internal fun NavigateUpIcon(onUpClick: () -> Unit) {
    IconButton(onClick = { onUpClick.invoke() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.navigation_description),
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorite: () -> Boolean,
    action: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    val coroutineScope = rememberCoroutineScope()
    val scale = remember {
        Animatable(1f)
    }
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    scale.animateTo(
                        0.8f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                }
                action(!isFavorite())
            },
            modifier = modifier
                .scale(scale = scale.value)
                .padding(dimensionResource(id = R.dimen.size_small)),
            interactionSource = remember {
                MutableInteractionSource()
            }

        ) {
            Icon(
                imageVector = if (isFavorite()) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = stringResource(id = R.string.fav_description),
                tint = Color.Red
            )
        }
    }
}

@Composable
internal fun ExpandButton(expanded: () -> Boolean, action: (Boolean) -> Unit) {
    IconButton(
        onClick = {
            action(!expanded())
        }) {
        val icon = if (expanded()) {
            Icons.Default.KeyboardArrowUp
        } else {
            Icons.Default.KeyboardArrowDown
        }
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = R.string.expand_Button_description)
        )
    }
}
