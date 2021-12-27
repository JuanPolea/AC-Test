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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.jfmr.ac.test.domain.model.ResultsItem
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListState
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen

@ExperimentalFoundationApi
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    onClick: (ResultsItem) -> Unit
) {
    //TODO fix scroll position on orientation changes
    val characterListViewModel: CharacterListViewModel = hiltViewModel()
    val characterListState by characterListViewModel.characterSF.collectAsState()
    val state = rememberLazyListState()
    when (characterListState) {
        is CharacterListState.Initial -> {}
        is CharacterListState.Error -> ErrorScreen("faksjdlñ")
        is CharacterListState.Success -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxWidth(),
                cells = GridCells.Adaptive(150.dp),
                state = state,
            ) {
                val listItems: List<ResultsItem> =
                    (characterListState as CharacterListState.Success).characters.results?.filterNotNull() ?: emptyList()
                items(listItems) { item ->
                    CharacterItemList(item, onClick)
                }
            }
        }
    }
}

@Composable
private fun CharacterItemList(
    resultsItem: ResultsItem,
    onClick: (ResultsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable { onClick(resultsItem) },
        shape = RoundedCornerShape(4.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Box(
            modifier = Modifier
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
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = resultsItem.image,
                    contentScale = ContentScale.FillWidth,
                )
                val padding = Modifier.padding(start = 8.dp, end = 4.dp)
                Text(
                    text = resultsItem.name ?: "",
                    modifier = padding,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = resultsItem.origin?.name ?: "",
                    modifier = padding,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = resultsItem.status ?: "",
                    modifier = padding,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}
