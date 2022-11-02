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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.episode.list.view.EpisodesScreen
import com.jfmr.ac.test.presentation.ui.main.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.main.component.HeartButton

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

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.character_detail_padding)), contentAlignment = Alignment.Center) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
            item {
                DetailHeader(
                    character = character,
                    lazyListState = lazyListState
                )
            }
            item {
                CharacterDetailBody(
                    character = character,
                    action = action
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .padding(
                            top = dimensionResource(id = R.dimen.character_list_padding)),
                )
            }
            item {
                character.episode?.filterNotNull()?.let { EpisodesScreen(episodes = it) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailBody(
    character: DomainCharacter,
    action: (DomainCharacter) -> Unit,
) {
    HeartButton(character, action, Alignment.TopEnd)
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = CutCornerShape(dimensionResource(id = R.dimen.corner_shape)),
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(
                            dimensionResource(id = R.dimen.row_padding),
                        ),
                    text = stringResource(id = R.string.character),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        Divider()
        DetailRow(R.string.name, character.name ?: stringResource(id = R.string.unknow))
        DetailRow(R.string.status, character.status ?: stringResource(id = R.string.unknow))
        DetailRow(R.string.location, character.location?.name ?: stringResource(id = R.string.unknow))
        DetailRow(R.string.gender, character.gender ?: stringResource(id = R.string.unknow))
        DetailRow(R.string.species, character.species ?: stringResource(id = R.string.unknow))
        DetailRow(R.string.origin, character.origin?.name ?: stringResource(id = R.string.unknow))
    }
}

@Composable
private fun DetailHeader(
    character: DomainCharacter,
    lazyListState: LazyListState,
) {
    var scrolledY = 0f
    var previousOffset = 0
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(character.image)
            .placeholder(R.drawable.ic_placeholder)
            .crossfade(true).build(),
        contentDescription = stringResource(id = R.string.image_detail_description),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimensionResource(id = R.dimen.character_detail_image_elevation),
                shape = CircleShape,
                spotColor = MaterialTheme.colorScheme.primary
            )
            .graphicsLayer {
                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.5f
                previousOffset = lazyListState.firstVisibleItemScrollOffset
            },
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_placeholder)
    )
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
