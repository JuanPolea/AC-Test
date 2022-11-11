package com.jfmr.ac.test.presentation.ui.episode.list.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    if (episodes.isNotEmpty()) {
        Spacer(modifier = Modifier.height(IntrinsicSize.Min))
        val state = rememberLazyListState()
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
                ) { index, item ->
                    EpisodeItemContent(item)
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EpisodeItemContent(episode: Episode) {
    Card(
        Modifier.padding(dimensionResource(id = R.dimen.row_padding)),
        shape = CutCornerShape(topEnd = dimensionResource(id = R.dimen.corner_shape)),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary)
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
