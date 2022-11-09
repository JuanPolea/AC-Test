package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jfmr.ac.test.presentation.ui.R

@Composable
fun ErrorScreen(
    messageResource: Int,
    retry: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = messageResource),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
            Image(
                painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = R.drawable.ic_pickle_rick)
                    .error(R.drawable.ic_placeholder)
                    .build()),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = stringResource(id = R.string.no_internet_connection),
                contentScale = ContentScale.FillWidth,
            )
            Button(
                onClick = { retry() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.character_list_padding))) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
