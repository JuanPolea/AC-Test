package com.jfmr.ac.test.presentation.ui.character.list.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jfmr.ac.test.domain.model.ResultsItem
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListState
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (ResultsItem) -> Unit,
) {
    val characterListState by characterListViewModel.characterSF.collectAsState()
    val state = rememberLazyGridState()

    Scaffold(
        topBar = {
            MainAppBar()
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (characterListState) {
                is CharacterListState.Initial -> CircularProgressIndicator()
                is CharacterListState.Error -> ErrorScreen("faksjdlñ")
                is CharacterListState.Success -> {
                    LazyVerticalGrid(
                        modifier = modifier
                            .fillMaxWidth(),
                        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
                        state = state,
                    ) {
                        val listItems: List<ResultsItem> =
                            (characterListState as CharacterListState.Success).characters.results?.filterNotNull()
                                ?: emptyList()
                        items(listItems) { item ->
                            CharacterListContent(item, onClick)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListContent(
    resultsItem: ResultsItem,
    onClick: (ResultsItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.character_list_padding))
            .clickable { onClick(resultsItem) },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = resultsItem.image)
                            .apply(
                                block = fun ImageRequest.Builder.() {
                                    size(Size.ORIGINAL)
                                }
                            )
                            .build()
                    ),
                    modifier = modifier.fillMaxWidth(),
                    contentDescription = resultsItem.image,
                    contentScale = ContentScale.FillWidth,
                )
                resultsItem.name?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.text_start),
                                end = dimensionResource(id = R.dimen.text_end)
                            ),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.spacer_bottom)))
            }
        }
    }
}
