package com.jfmr.ac.test.presentation.ui.character.list.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.jfmr.ac.test.domain.model.ResultsItem
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListState
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.main.theme.Shapes

@ExperimentalFoundationApi
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (ResultsItem) -> Unit,
) {
    val characterListState by characterListViewModel.characterSF.collectAsState()
    val state = rememberLazyListState()
    when (characterListState) {
        is CharacterListState.Initial -> CircularProgressIndicator()
        is CharacterListState.Error -> ErrorScreen("faksjdlñ")
        is CharacterListState.Success -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxWidth(),
                cells = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
                state = state,
            ) {
                val listItems: List<ResultsItem> =
                    (characterListState as CharacterListState.Success).characters.results?.filterNotNull() ?: emptyList()
                items(listItems) { item ->
                    CharacterListContent(item, onClick)
                }
            }
        }
    }
}

@Composable
private fun CharacterListContent(
    resultsItem: ResultsItem,
    onClick: (ResultsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.character_list_padding))
            .clickable { onClick(resultsItem) },
        shape = Shapes.large,
        elevation = dimensionResource(id = R.dimen.character_list_elevation),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            Column {
                Image(
                    painter = rememberImagePainter(
                        data = resultsItem.image,
                        builder = {
                            size(OriginalSize)
                        }
                    ),
                    modifier = modifier.fillMaxWidth(),
                    contentDescription = resultsItem.image,
                    contentScale = ContentScale.FillWidth,
                )
                resultsItem.name?.let { EclipsedText(it, MaterialTheme.typography.subtitle1) }
                resultsItem.origin?.name?.let { EclipsedText(it, MaterialTheme.typography.caption) }
                resultsItem.status?.let { EclipsedText(it, MaterialTheme.typography.body2) }
                Spacer(modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.spacer_bottom)))
            }
        }
    }
}

@Composable
private fun EclipsedText(title: String, textStyle: TextStyle) {
    Text(
        text = title,
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.text_start),
            end = dimensionResource(id = R.dimen.text_end)
        ),
        style = textStyle,
        maxLines = R.dimen.max_line_1,
        overflow = TextOverflow.Ellipsis
    )
}
