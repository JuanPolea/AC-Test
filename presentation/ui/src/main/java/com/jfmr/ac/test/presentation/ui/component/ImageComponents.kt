package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.jfmr.ac.test.presentation.ui.R

@Composable
internal fun ImageFromUrlFullWidth(url: () -> String) {
    ImageFromUrl(url = url,
        Modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimensionResource(id = R.dimen.size_small_medium),
                shape = CutCornerShape(dimensionResource(id = R.dimen.size_small)),
                spotColor = MaterialTheme.colorScheme.primary
            )
    )
}

@Composable
internal fun ImageFromUrlLandScape(url: () -> String) {
    val size = dimensionResource(id = R.dimen.size_small)
    ImageFromUrl(
        url = url,
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .shadow(
                elevation = dimensionResource(id = R.dimen.size_small_medium),
                shape = CutCornerShape(size),
                spotColor = MaterialTheme.colorScheme.primary
            )
            .clip(CutCornerShape(size))
    )
}

@Composable
private fun ImageFromUrl(
    url: () -> String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(url())
            .placeholder(R.drawable.ic_placeholder)
            .size(Size.ORIGINAL)
            .crossfade(true).build(),
        contentDescription = stringResource(id = R.string.image_detail_description),
        modifier = modifier,
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_placeholder),
    )
}
