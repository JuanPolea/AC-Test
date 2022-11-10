package com.jfmr.ac.test.presentation.ui.character.detail.view

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.jfmr.ac.test.presentation.ui.component.ExpandableContent
import com.jfmr.ac.test.presentation.ui.component.HeartButton
import com.jfmr.ac.test.presentation.ui.episode.list.view.EpisodesScreen

const val EXPAND_ANIMATION_DURATION: Int = 200
const val EXPANSTION_TRANSITION_DURATION: Int = 500

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
    character: Character,
    action: (Character) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier
        .fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
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
                //   character.episode?.filterNotNull()?.let { EpisodesScreen(episodes = it) }
                character.episode?.filterNotNull()?.let { EpisodesContent(list = it) }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .padding(
                            top = dimensionResource(id = R.dimen.character_list_padding)),
                )
            }

        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesContent(list: List<String>) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val transitionState = remember {
        MutableTransitionState(expanded)
            .apply {
                targetState = !expanded
            }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "bgColorTransition") {
        if (expanded) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        }
    }
    val cardRoundedCorners by transition.animateDp(
        {
            tween(
                durationMillis = EXPAND_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        },
        label = "cardRoundedCorners",
    ) {
        if (expanded) 4.dp else dimensionResource(id = R.dimen.corner_shape)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp
            ),
        shape = CutCornerShape(cardRoundedCorners),
        containerColor = cardBgColor,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.row_padding)),
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.titleLarge,
            )
            IconButton(onClick = {
                expanded = !expanded
            }) {
                val icon = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                }
                Icon(imageVector = icon, contentDescription = "drop")
            }
        }

        ExpandableContent(
            visible = expanded,
            initialVisibility = expanded,
            enterTransition = expandHorizontally(
                expandFrom = Alignment.Start,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ),
            exitTransition = shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ) + fadeOut(
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            )
        ) {
            EpisodesScreen(episodes = list)
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailBody(
    character: Character,
    action: (Character) -> Unit,
) {
    HeartButton(character, action, Alignment.TopEnd)
    var expanded by remember {
        mutableStateOf(false)
    }
    val transitionState = remember {
        MutableTransitionState(expanded)
            .apply {
                targetState = !expanded
            }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "bgColorTransition") {
        if (expanded) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        }
    }
    val cardRoundedCorners by transition.animateDp(
        {
            tween(
                durationMillis = EXPAND_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        },
        label = "cardRoundedCorners",
    ) {
        if (expanded) 4.dp else dimensionResource(id = R.dimen.corner_shape)
    }
    val contentColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "contentColor") {
        if (expanded) Color.White else Color.Black
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                //horizontal = cardPaddingHorizontal,
                vertical = 12.dp
            ),
        shape = CutCornerShape(cardRoundedCorners),
        containerColor = cardBgColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            IconButton(onClick = {
                expanded = !expanded
            }) {
                val icon = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                }
                Icon(imageVector = icon, contentDescription = "drop")
            }
        }
        if (expanded) {
            Divider()
        }
        ExpandableContent(
            visible = expanded,
            initialVisibility = expanded,
            enterTransition = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ),
            exitTransition =
            shrinkVertically(
                // Expand from the top.
                shrinkTowards = Alignment.Top,
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ) + fadeOut(
                // Fade in with the initial alpha of 0.3f.
                animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
            ),
            onExpanded = {
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
            })
    }
}


@Composable
private fun DetailHeader(
    character: Character,
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
            .padding(dimensionResource(id = R.dimen.character_detail_padding))
            .shadow(
                elevation = dimensionResource(id = R.dimen.character_detail_image_elevation),
                shape = CutCornerShape(12.dp),
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
