package com.jfmr.ac.test.presentation.ui.character.detail.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailUI
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.component.ExpandButton
import com.jfmr.ac.test.presentation.ui.component.ExpandableContent
import com.jfmr.ac.test.presentation.ui.component.FavoriteButton
import com.jfmr.ac.test.presentation.ui.component.ImageFromUrlFullWidth
import com.jfmr.ac.test.presentation.ui.component.ImageFromUrlLandScape
import com.jfmr.ac.test.presentation.ui.component.NavigateUpIcon
import com.jfmr.ac.test.presentation.ui.episode.list.model.EpisodeUI
import org.jetbrains.annotations.VisibleForTesting

const val EXPAND_ANIMATION_DURATION: Int = 200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterDetailScreen(
    onUpClick: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val characterDetailState by detailViewModel.characterDetailState.collectAsState()
    detailViewModel.getData()
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
                FavoriteButton(
                    isFavorite = { characterDetailState.character.isFavorite },
                    action = {
                        detailViewModel.onEvent(CharacterDetailEvent.UpdateCharacter(characterDetailState.copy(character = characterDetailState.character.copy(
                            isFavorite = it))))
                    },
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background,
            ),
        )
    }) {
        CharacterDetailContent(
            characterDetail = { characterDetailState },
            action = { detailViewModel.getData() },
        )
    }

}

@VisibleForTesting
@Composable
/* package*/ fun CharacterDetailContent(
    characterDetail: () -> CharacterDetailUI,
    action: () -> Unit,
) {
    when {
        characterDetail().isLoading == true ->
            CircularProgressBar(stringResource(id = R.string.retrieving_characters))
        characterDetail().error != null ->
            ErrorScreen(messageResource = R.string.character_detail_not_found) { action() }
        else -> with(characterDetail().character) {
            val lazyListState = rememberLazyListState()
            val description = stringResource(id = R.string.detail_list)
            Box(contentAlignment = Alignment.Center) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { contentDescription = description },
                    state = lazyListState,
                ) {
                    item {
                        DetailHeader { this@with }
                    }
                    item {
                        CharacterDetailBody(character = { this@with })
                    }
                    item {
                        CharacterDetailFooter { characterDetail().episodes }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailHeader(
    character: () -> CharacterUI,
) {
    if (LocalConfiguration.current.orientation != Configuration.ORIENTATION_LANDSCAPE) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.size_medium_high))
            .shadow(elevation = dimensionResource(id = R.dimen.size_small_medium),
                shape = CutCornerShape(dimensionResource(id = R.dimen.size_medium)),
                spotColor = MaterialTheme.colorScheme.primary)) {
            ImageFromUrlFullWidth(
                url = { character().image },
            )
        }
    }
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun CharacterDetailBody(
    character: () -> CharacterUI,
) {
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = stringResource(R.string.transitionLabel))

    val cardBgColor by transition.animateColor({
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
            tween(durationMillis = EXPAND_ANIMATION_DURATION, easing = FastOutSlowInEasing)
        },
        label = stringResource(R.string.rounded_corners_label),
    ) {
        if (expanded) {
            dimensionResource(id = R.dimen.size_small_medium)
        } else {
            dimensionResource(id = R.dimen.size_medium)
        }
    }
    CharacterDetailBodyContent(cardRoundedCorners = { cardRoundedCorners },
        cardBgColor = { cardBgColor },
        character = { character() },
        expanded = { expanded },
        action = { expanded = it })
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterDetailBodyContent(
    cardRoundedCorners: () -> Dp,
    cardBgColor: () -> Color,
    character: () -> CharacterUI,
    expanded: () -> Boolean,
    action: (Boolean) -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.size_medium_high))
        .fillMaxWidth()
        .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)),
        shape = CutCornerShape(cardRoundedCorners()),
        containerColor = cardBgColor(),
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.size_small_medium),
            ), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(
                        dimensionResource(id = R.dimen.size_small_medium),
                    ),
                text = character().name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            ExpandButton(
                expanded = {
                    expanded()
                },
                action = {
                    action(it)
                }
            )
        }
        if (expanded()) {
            Divider()
        }
        ExpandableContent(visible = expanded()) {
            CharacterDetailBodyCard(character())
        }
    }
}

@Composable
private fun CharacterDetailBodyCard(
    character: CharacterUI,
) = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.size_medium_high)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CharacterDescriptionContent(character, Modifier.fillMaxWidth(0.5f))
        ImageFromUrlLandScape { character.image }
    }
} else {
    CharacterDescriptionContent(character, Modifier.fillMaxWidth())
}

@Composable
private fun CharacterDescriptionContent(character: CharacterUI, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.size_small_medium)),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        with(character) {
            DetailRowContent(R.string.status) { status }
            DetailRowContent(R.string.location) { location.name }
            DetailRowContent(R.string.gender) { gender }
            DetailRowContent(R.string.species) { species }
            DetailRowContent(R.string.origin) { origin.name }
        }
    }
}


@Composable
private fun DetailRowContent(nameResource: Int, value: () -> String) {
    Row(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.size_medium_high),
            end = dimensionResource(id = R.dimen.size_medium_high),
            bottom = dimensionResource(id = R.dimen.size_medium_high),
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = nameResource),
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            text = value().ifEmpty { stringResource(id = R.string.unknown) },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Composable
internal fun CharacterDetailFooter(episodes: () -> List<EpisodeUI>) {
    val density = LocalDensity.current
    val state = rememberLazyListState()
    val description = stringResource(id = R.string.episodes)
    Column(modifier = Modifier
        .fillMaxWidth()
        .semantics { contentDescription = description }, verticalArrangement = Arrangement.SpaceAround) {
        AnimatedVisibility(visible = episodes().isNotEmpty(),
            enter = slideInHorizontally(
                initialOffsetX = { with(density) { 10.dp.roundToPx() } },
            ) + expandHorizontally(
                expandFrom = Alignment.Start,
            ) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()) {
            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.size_medium_high)),
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        AnimatedVisibility(visible = episodes().isNotEmpty(),
            enter = slideInHorizontally(animationSpec = tween(300, easing = { OvershootInterpolator().getInterpolation(it) }),
                initialOffsetX = { 400 }) + fadeIn(),
            exit = fadeOut()) {
            LazyRow(state = state, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, content = {
                itemsIndexed(items = episodes(), key = { _, item ->
                    item.id
                }) { _, item ->
                    EpisodeItemContent(item)
                }
            })
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun EpisodeItemContent(episode: EpisodeUI) {
    Card(
        Modifier
            .padding(dimensionResource(id = R.dimen.size_small_medium))
            .shadow(elevation = dimensionResource(id = R.dimen.size_small_medium),
                shape = CutCornerShape(topEnd = dimensionResource(id = R.dimen.size_medium))),
        shape = CutCornerShape(topEnd = dimensionResource(id = R.dimen.size_medium)),
        border = BorderStroke(dimensionResource(id = R.dimen.size_small), MaterialTheme.colorScheme.primary),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Text(text = episode.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.size_medium),
                    start = dimensionResource(id = R.dimen.size_medium),
                    end = dimensionResource(id = R.dimen.size_medium),
                ))
        Text(text = episode.episode,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.size_medium),
                    start = dimensionResource(id = R.dimen.size_medium),
                    end = dimensionResource(id = R.dimen.size_medium),
                ))
        Text(text = episode.airDate,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.size_medium)))
    }
}
