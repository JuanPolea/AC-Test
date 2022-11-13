package com.jfmr.ac.test.presentation.ui.character.detail.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.component.ExpandButton
import com.jfmr.ac.test.presentation.ui.component.ExpandableContent
import com.jfmr.ac.test.presentation.ui.component.FavoriteButton
import com.jfmr.ac.test.presentation.ui.component.NavigateUpIcon
import com.jfmr.ac.test.presentation.ui.episode.list.view.EpisodesScreen

const val EXPAND_ANIMATION_DURATION: Int = 200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onUpClick: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val characterDetailState = detailViewModel.characterDetailState
    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.character_detail),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                NavigateUpIcon(onUpClick)
            },
            actions = {
                if (characterDetailState is CharacterDetailState.Success) {
                    val character = characterDetailState.characterDetail
                    FavoriteButton(
                        isFavorite = character.isFavorite,
                        action = { detailViewModel.updateCharacter(character.copy(isFavorite = it)) },
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background,
            ),
        )
    }) {
        when (characterDetailState) {
            is CharacterDetailState.Loading -> CircularProgressBar(stringResource(id = characterDetailState.messageResource))
            is CharacterDetailState.Error -> ErrorScreen(messageResource = R.string.character_detail_not_found) {}
            is CharacterDetailState.Success -> CharacterDetailContent(characterDetailState.characterDetail)
        }
    }

}

@Composable
private fun CharacterDetailContent(character: Character) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                DetailHeader(
                    character = character,
                    lazyListState = lazyListState,
                )
            }
            item {
                CharacterDetailBody(character = character)
            }
            item {
                character
                    .episode
                    .filter {
                        it.isNotEmpty()
                    }.let {
                        EpisodesContent(list = it)
                    }
            }
        }
    }
}

@Composable
fun EpisodesContent(list: List<String>) {
    val visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally {
            with(density) { -40.dp.roundToPx() }
        } + expandHorizontally(
            expandFrom = Alignment.Start
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
    ) {
        EpisodesScreen(episodes = list)
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun CharacterDetailBody(
    character: Character,
) {
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded)
            .apply {
                targetState = !expanded
            }
    }
    val transition = updateTransition(targetState = transitionState, label = stringResource(R.string.transitionLabel))

    val cardBgColor by transition.animateColor(
        {
            tween(durationMillis = EXPAND_ANIMATION_DURATION)
        }, label = stringResource(R.string.background_transition_label)) {
        if (expanded) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        }
    }
    val cardRoundedCorners by transition.animateDp(
        {
            tween(
                durationMillis = EXPAND_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        },
        label = stringResource(R.string.rounded_corners_label),
    ) {
        if (expanded) {
            dimensionResource(id = R.dimen.corner_shape_small)
        } else {
            dimensionResource(id = R.dimen.corner_shape)
        }
    }
    CharacterDetailBodyContent(
        cardRoundedCorners = cardRoundedCorners,
        cardBgColor = cardBgColor,
        character = character,
        expanded = expanded,
        action = { expanded = it }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterDetailBodyContent(
    cardRoundedCorners: Dp,
    cardBgColor: Color,
    character: Character,
    expanded: Boolean,
    action: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.character_detail_padding)),
        shape = CutCornerShape(cardRoundedCorners),
        containerColor = cardBgColor,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    dimensionResource(id = R.dimen.row_padding),
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.row_padding),
                    ),
                text = character.name ?: stringResource(id = R.string.character),
                style = MaterialTheme.typography.titleLarge,
            )
            ExpandButton(
                expanded = expanded,
                action = {
                    action(it)
                }
            )
        }
        if (expanded) {
            Divider()
        }
        ExpandableContent(visible = expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                with(character) {
                    DetailRow(R.string.status, status)
                    DetailRow(R.string.location, location?.name)
                    DetailRow(R.string.gender, gender)
                    DetailRow(R.string.species, species)
                    DetailRow(R.string.origin, origin?.name)
                }
            }
        }
    }
}


@Composable
private fun DetailHeader(
    character: Character,
    lazyListState: LazyListState,
) {
    var scrolledY = 0f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.character_detail_padding))
            .shadow(
                elevation = dimensionResource(id = R.dimen.card_elevation),
                shape = CutCornerShape(dimensionResource(id = R.dimen.corner_shape)),
                spotColor = MaterialTheme.colorScheme.primary
            )
    )
    {
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
                    elevation = dimensionResource(id = R.dimen.card_elevation),
                    shape = CutCornerShape(dimensionResource(id = R.dimen.corner_shape)),
                    spotColor = MaterialTheme.colorScheme.primary
                )
                .graphicsLayer {
                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                    translationY = scrolledY * 0.5f
                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                },
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_placeholder),
        )
    }
}


@Composable
private fun DetailRow(nameResource: Int?, value: String?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = dimensionResource(id = R.dimen.character_detail_padding),
            end = dimensionResource(id = R.dimen.character_detail_padding),
            bottom = dimensionResource(id = R.dimen.character_detail_padding),
        ), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(id = nameResource ?: R.string.unknow),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = value ?: stringResource(id = R.string.unknow),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
