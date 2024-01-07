package com.jfmr.ac.test.presentation.ui.character.list.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListEvent
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.component.FavoriteButton
import com.jfmr.ac.test.presentation.ui.component.MainAppBar
import kotlinx.coroutines.launch

@Composable
internal fun CharacterListRoute(
    onClick: (CharacterUI) -> Unit,
    modifier: Modifier = Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val lazyPagingItems: LazyPagingItems<CharacterUI> =
        characterListViewModel.pager.collectAsLazyPagingItems()

    Scaffold(topBar = {
        MainAppBar()
    }, snackbarHost = {
        SnackbarHost(snackBarHostState) {
            Snackbar(
                snackbarData = it,
            )
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            CharacterListSuccessContent(
                modifier = modifier,
                items = lazyPagingItems,
                onClick = onClick,
                addToFavorites = {
                    characterListViewModel.onEvent(CharacterListEvent.AddToFavorite(it))
                },
            )
            if (
                lazyPagingItems.loadState.source.refresh is LoadState.Loading
                || lazyPagingItems.loadState.append is LoadState.Loading
                || lazyPagingItems.loadState.refresh is LoadState.Loading
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyGridState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyGridState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> rememberLazyGridState()
    }
}

@Composable
private fun CharacterListSuccessContent(
    items: LazyPagingItems<CharacterUI>,
    onClick: (CharacterUI) -> Unit,
    addToFavorites: (CharacterUI) -> Unit,
    modifier: Modifier,
) {
    val scope = rememberCoroutineScope()
    val state = items.rememberLazyListState()
    val shouldShowScrollToUp =
        remember(state) {
            derivedStateOf {
                state.firstVisibleItemIndex > 0
            }
        }
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
        state = state,
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id },
            contentType = items.itemContentType { it.type }
        ) { index ->
            items[index]?.let {
                CharacterItemListContent(
                    character = it,
                    onClick = onClick,
                    addToFavorites = addToFavorites
                )
            }
        }
    }
    ScrollToTopButton(showButton = shouldShowScrollToUp.value) {
        scope.launch {
            state.scrollToItem(0)
        }
    }
}


@Composable
private fun ScrollToTopButton(
    showButton: Boolean,
    scrollToTop: () -> Unit,
) {
    AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Button(
                onClick = {
                    scrollToTop()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.scroll_up),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

    }
}

@Composable
private fun CharacterItemListContent(
    character: CharacterUI,
    onClick: (CharacterUI) -> Unit,
    addToFavorites: (CharacterUI) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.size_small))
            .shadow(
                elevation = dimensionResource(id = R.dimen.size_small_medium),
                shape = CutCornerShape(dimensionResource(id = R.dimen.size_medium)),
                spotColor = MaterialTheme.colorScheme.primary
            )
            .clickable { onClick(character) },
        shape = CutCornerShape(size = 12.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = character.image)
                            .placeholder(R.drawable.ic_placeholder).crossfade(true)
                            .apply(block = fun ImageRequest.Builder.() {
                                size(Size.ORIGINAL)
                            }).error(R.drawable.ic_placeholder).build()
                    ),
                    modifier = modifier.fillMaxWidth(),
                    contentDescription = character.image,
                    contentScale = ContentScale.FillWidth,
                )
                FavoriteButton(
                    isFavorite = { character.isFavorite },
                    action = {
                        addToFavorites(
                            character.copy(isFavorite = !character.isFavorite)
                        )
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            Text(
                text = character.name,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.size_medium),
                    start = dimensionResource(id = R.dimen.size_medium),
                    end = dimensionResource(id = R.dimen.size_medium),
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.size_medium)))
        }
    }
}
