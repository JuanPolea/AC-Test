package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.jfmr.ac.test.presentation.ui.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = action,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = stringResource(id = R.string.fav_description),
            tint = Color.Red
        )

    }
}
