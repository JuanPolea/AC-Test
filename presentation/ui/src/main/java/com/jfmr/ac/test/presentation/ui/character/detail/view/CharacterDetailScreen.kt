package com.jfmr.ac.test.presentation.ui.character.detail.view

import androidx.compose.foundation.clickable
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
import coil.size.Size
import com.jfmr.ac.test.domain.model.character.DomainCharacter
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
        },
            navigationIcon = {
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
            is CharacterDetailState.Error -> ErrorScreen(messageResource = R.string.character_detail_not_found) {}
            is CharacterDetailState.Success -> CharacterDetailContent(characterDetailState.characterDetail) {
                detailViewModel.updateCharacter(it)
            }
        }
    }

}

@Composable
private fun CharacterDetailContent(
    character: DomainCharacter,
    action: (DomainCharacter) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
            item {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(character.image)
                        .placeholder(R.drawable.ic_placeholder)
                        .crossfade(true).build(),
                    contentDescription = stringResource(id = R.string.image_detail_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledY * 0.5f
                            previousOffset = lazyListState.firstVisibleItemScrollOffset
                        }
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth)
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.row_padding)),
                        text = stringResource(id = R.string.character),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                if (character.isFavorite == true) {
                                    R.drawable.ic_favorite_filled
                                } else {
                                    R.drawable.ic_favorite_border
                                }
                            )
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen.row_padding))
                            .clickable {
                                action(character.copy(isFavorite = !character.isFavorite!!))
                            },
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(id = R.string.fav_description)
                    )
                }
            }
            item {
                DetailRow(R.string.name, character.name ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.status, character.status ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.location, character.location?.name ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.gender, character.gender ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.species, character.species ?: stringResource(id = R.string.unknow))
            }
            item {
                DetailRow(R.string.origin, character.origin?.name ?: stringResource(id = R.string.unknow))
            }
            item {
                Spacer(modifier = Modifier.height(IntrinsicSize.Max))
            }
            item {
                character.episode?.filterNotNull()?.let { EpisodesScreen(episodes = it) }
            }

        }
    }
}

@Composable
private fun DetailRow(nameResource: Int, value: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = dimensionResource(id = R.dimen.character_detail_padding),
            end = dimensionResource(id = R.dimen.character_detail_padding),
            bottom = dimensionResource(id = R.dimen.character_detail_padding),
        ), verticalAlignment = Alignment.CenterVertically) {
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
