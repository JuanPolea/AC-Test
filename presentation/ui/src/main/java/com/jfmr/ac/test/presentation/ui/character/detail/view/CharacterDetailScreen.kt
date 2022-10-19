package com.jfmr.ac.test.presentation.ui.character.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.episode.list.view.EpisodesScreen
import com.jfmr.ac.test.presentation.ui.main.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onUpClick: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val characterDetailState = detailViewModel.characterDetailState
    Scaffold(topBar = {
        SmallTopAppBar(title = {
            Text(text = "Detail")
        }, navigationIcon = {
            IconButton(onClick = { onUpClick.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background,
            ))
    }) {
        when (characterDetailState) {
            is CharacterDetailState.Loading -> CircularProgressBar(stringResource(id = characterDetailState.messageResource))
            is CharacterDetailState.Error -> ErrorScreen(text = stringResource(id = R.string.character_detail_not_found))
            is CharacterDetailState.Success -> CharacterDetailContent(characterDetailState.characterDetail)
        }
    }

}

@Composable
private fun CharacterDetailContent(characterDetail: CharacterDetail) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                AsyncImage(model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(characterDetail.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                    contentDescription = stringResource(id = R.string.image_detail_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledY * 0.5f
                            previousOffset = lazyListState.firstVisibleItemScrollOffset
                        }
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = dimensionResource(id = R.dimen.row_padding)),
                    text = stringResource(id = R.string.character),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            item {
                DetailRow(R.string.name, characterDetail.name)
            }
            item {
                DetailRow(R.string.status, characterDetail.status)
            }
            item {
                DetailRow(R.string.location, characterDetail.location?.name ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.gender, characterDetail.gender)
            }
            item {
                DetailRow(R.string.species, characterDetail.species)
            }
            item {
                DetailRow(R.string.origin, characterDetail.origin?.name ?: stringResource(id = R.string.unknow))
            }
            item {
                Spacer(modifier = Modifier.height(IntrinsicSize.Max))
            }
            item {
                EpisodesScreen(episodes = characterDetail.episode)
            }

        }
    }
}

@Composable
private fun DetailRow(nameResource: Int, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.character_detail_padding),
                end = dimensionResource(id = R.dimen.character_detail_padding),
                bottom = dimensionResource(id = R.dimen.character_detail_padding),
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = nameResource),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
