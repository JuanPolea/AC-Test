package com.jfmr.ac.test.presentation.ui.episode.list.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.episode.list.viewmodel.EpisodeViewModel

@Composable
fun EpisodesScreen(
    episodes: List<String>,
    episodeViewModel: EpisodeViewModel = hiltViewModel(),
) {
    val domainEpisodes by episodeViewModel.episodes.collectAsState()
    episodeViewModel.episodes(episodes)
    EpisodesRowContent(domainEpisodes)

}

@Composable
private fun EpisodesRowContent(episodes: List<Episode>) {
    val state = rememberLazyListState()
    val visibleState = remember {
        MutableTransitionState(episodes.isNotEmpty()).apply {
            targetState = episodes.isEmpty()
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        AnimatedVisibility(visible = visibleState.targetState, enter = fadeIn()) {
            Text(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.character_detail_padding)),
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        AnimatedVisibility(visible = episodes.isNotEmpty(),
            enter = slideInHorizontally { +400 } + fadeIn(),
            exit = fadeOut
                ()) {
            LazyRow(
                state = state,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    itemsIndexed(
                        items = episodes,
                        key = { _, item ->
                            item.id
                        }
                    ) { _, item ->
                        EpisodeItemContent(item)
                    }
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EpisodeItemContent(episode: Episode) {
    Card(
        Modifier
            .padding(dimensionResource(id = R.dimen.row_padding))
            .shadow(
                elevation = dimensionResource(id = R.dimen.card_elevation),
                shape = CutCornerShape(topEnd = dimensionResource(id = R.dimen.corner_shape))
            ),
        shape = CutCornerShape(topEnd = dimensionResource(id = R.dimen.corner_shape)),
        border = BorderStroke(dimensionResource(id = R.dimen.border_stroke), MaterialTheme.colorScheme.primary),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Text(
            text = episode.name ?: stringResource(id = R.string.unknow),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.corner_shape),
                    start = dimensionResource(id = R.dimen.corner_shape),
                    end = dimensionResource(id = R.dimen.corner_shape),
                )
        )
        Text(
            text = episode.episode ?: stringResource(id = R.string.unknow),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.corner_shape),
                    start = dimensionResource(id = R.dimen.corner_shape),
                    end = dimensionResource(id = R.dimen.corner_shape),
                )
        )
        Text(
            text = episode.airDate ?: stringResource(id = R.string.unknow),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.corner_shape))
        )
    }
}
