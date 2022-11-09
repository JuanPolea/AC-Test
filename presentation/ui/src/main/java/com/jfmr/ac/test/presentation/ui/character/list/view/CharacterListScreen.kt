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
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.component.HeartButton
import com.jfmr.ac.test.presentation.ui.component.extensions.ListExtensions.gridItems
import com.jfmr.ac.test.presentation.ui.component.MainAppBar
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (Character) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val lazyPagingItems: LazyPagingItems<Character> = characterListViewModel.pager.collectAsLazyPagingItems()

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
            .padding(padding), contentAlignment = Alignment.Center) {
            CharacterListContent(
                modifier = modifier,
                onClick = onClick,
                onRefresh = { lazyPagingItems.refresh() },
                isRefreshing = { lazyPagingItems.loadState.refresh == LoadState.Loading && lazyPagingItems.itemCount == 0 },
                items = { lazyPagingItems },
                addToFavorites = {
                    characterListViewModel.addToFavorite(it)
                },
            )
        }
    }
}

@Composable
private fun CharacterListContent(
    modifier: Modifier,
    onClick: (Character) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: () -> Boolean,
    items: () -> LazyPagingItems<Character>,
    addToFavorites: (Character) -> Unit,
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

    Timber.wtf(items().loadState.toString())
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
    modifier: Modifier,
    state: () -> LazyGridState,
    items: () -> LazyPagingItems<Character>,
    onClick: (Character) -> Unit,
    showScrollToTopButton: () -> State<Boolean>,
    addToFavorites: (Character) -> Unit,
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
                Text(text = stringResource(id = R.string.scroll_to_top), style = MaterialTheme.typography.labelMedium)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemListContent(
    character: () -> Character,
    onClick: (Character) -> Unit,
    addToFavorites: (Character) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.character_list_padding))
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
                HeartButton(
                    character = character(),
                    action = {
                        addToFavorites(
                            character().copy(isFavorite = it.isFavorite)
                        )
                    },
                    alignment = Alignment.TopEnd
                )
            }
            Text(text = character().name ?: stringResource(id = R.string.unknow),
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.text),
                    start = dimensionResource(id = R.dimen.text),
                    end = dimensionResource(id = R.dimen.text),
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Spacer(modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.spacer_bottom)))
        }
    }
}
