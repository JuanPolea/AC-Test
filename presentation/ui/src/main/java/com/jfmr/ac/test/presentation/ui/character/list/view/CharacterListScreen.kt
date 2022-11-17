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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListEvent
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.component.FavoriteButton
import com.jfmr.ac.test.presentation.ui.component.MainAppBar
import com.jfmr.ac.test.presentation.ui.component.extensions.ListExtensions.gridItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (CharacterUI) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val lazyPagingItems: LazyPagingItems<CharacterUI> = characterListViewModel.pager.collectAsLazyPagingItems()

    Scaffold(topBar = {
        MainAppBar()
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) {
            Snackbar(
                snackbarData = it,
            )
        }
    }) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            CharacterListContent(
                onClick = onClick,
                onRefresh = { lazyPagingItems.refresh() },
                isRefreshing = { lazyPagingItems.loadState.refresh == LoadState.Loading && lazyPagingItems.itemCount == 0 },
                items = { lazyPagingItems },
                addToFavorites = { characterListViewModel.onEvent(CharacterListEvent.AddToFavorite(it)) },
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun CharacterListContent(
    onClick: (CharacterUI) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: () -> Boolean,
    items: () -> LazyPagingItems<CharacterUI>,
    addToFavorites: (CharacterUI) -> Unit,
    modifier: Modifier,
) {
    val lazyGridState = rememberLazyGridState()
    val showScrollToTopButton = remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex > 0
        }
    }
    val swipeState = rememberSwipeRefreshState(isRefreshing = isRefreshing())
    SwipeRefresh(
        state = swipeState,
        onRefresh = { onRefresh() }
    ) {
        when (items().loadState.mediator?.refresh) {
            is LoadState.Loading -> CircularProgressBar()
            is LoadState.Error -> ErrorScreen(R.string.error_retrieving_characters) { onRefresh() }
            else -> CharacterListSuccessContent(modifier = modifier,
                state = { lazyGridState },
                items = items,
                onClick = onClick,
                showScrollToTopButton = { showScrollToTopButton },
                addToFavorites = addToFavorites)
        }
    }

    if (
        items().loadState.source.refresh is LoadState.Loading
        || items().loadState.append is LoadState.Loading
        || items().loadState.refresh is LoadState.Loading
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun CharacterListSuccessContent(
    state: () -> LazyGridState,
    items: () -> LazyPagingItems<CharacterUI>,
    onClick: (CharacterUI) -> Unit,
    showScrollToTopButton: () -> State<Boolean>,
    addToFavorites: (CharacterUI) -> Unit,
    modifier: Modifier,
) {
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
        state = state(),
    ) {
        gridItems(items = items(), key = {
            it.id
        }, itemContent = { domainCharacter ->
            domainCharacter?.let {
                CharacterItemListContent(character = { it }, onClick = onClick, addToFavorites = addToFavorites)
            }
        })
    }
    ScrollToTopButton(showButton = { showScrollToTopButton().value }) {
        scope.launch {
            state().scrollToItem(0)
        }
    }
}

@Composable
private fun ScrollToTopButton(
    showButton: () -> Boolean,
    scrollToTop: () -> Unit,
) {

    AnimatedVisibility(visible = showButton(), enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Button(
                onClick = {
                    scrollToTop()
                },
            ) {
                Text(text = stringResource(id = R.string.scroll_up), style = MaterialTheme.typography.labelMedium)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemListContent(
    character: () -> CharacterUI,
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
            .clickable { onClick(character()) },
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
                    painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = character().image)
                        .placeholder(R.drawable.ic_placeholder).crossfade(true).apply(block = fun ImageRequest.Builder.() {
                            size(Size.ORIGINAL)
                        }).error(R.drawable.ic_placeholder).build()),
                    modifier = modifier.fillMaxWidth(),
                    contentDescription = character().image,
                    contentScale = ContentScale.FillWidth,
                )
                FavoriteButton(
                    isFavorite = { character().isFavorite },
                    action = {
                        addToFavorites(
                            character().copy(isFavorite = !character().isFavorite)
                        )
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            Text(text = character().name,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.size_medium),
                    start = dimensionResource(id = R.dimen.size_medium),
                    end = dimensionResource(id = R.dimen.size_medium),
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Spacer(modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.size_medium)))
        }
    }
}
