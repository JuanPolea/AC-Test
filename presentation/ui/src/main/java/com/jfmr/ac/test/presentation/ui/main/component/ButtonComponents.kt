package com.jfmr.ac.test.presentation.ui.main.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.presentation.ui.R
import kotlinx.coroutines.launch

@Composable
internal fun HeartButton(
    character: Character,
    action: (Character) -> Unit,
    alignment: Alignment = Alignment.Center,
) {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()
    val scale = remember {
        Animatable(1f)
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = if (character.isFavorite) {
                Icons.Outlined.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = stringResource(id = R.string.fav_description),
            tint = Color.Red,
            modifier = Modifier
                .scale(scale = scale.value)
                .size(size = dimensionResource(id = R.dimen.favorite_size))
                .align(alignment)
                .padding(dimensionResource(id = R.dimen.character_list_padding))
                .clickable(interactionSource = interactionSource, indication = null) {
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
                    action(character.copy(isFavorite = !character.isFavorite))
                }
        )
    }
}
