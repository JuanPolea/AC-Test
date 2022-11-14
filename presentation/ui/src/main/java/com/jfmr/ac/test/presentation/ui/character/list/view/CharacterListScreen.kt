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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                onClick = { c -> onClick(c) },
                onRefresh = { lazyPagingItems.refresh() },
                isRefreshing = {
                    lazyPagingItems.loadState.refresh == LoadState.Loading && lazyPagingItems.itemCount == 0
                },
                items = { lazyPagingItems },
            ) {
                characterListViewModel.onEvent(CharacterListEvent.AddToFavorite(it))
            }
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
) {

    val swipeState = rememberSwipeRefreshState(isRefreshing = isRefreshing())
    val refreshState by rememberSaveable {
        mutableStateOf(items().loadState.mediator?.refresh)
    }
    SwipeRefresh(state = swipeState, onRefresh = { onRefresh() }) {
        when (refreshState) {
            is LoadState.Loading ->
                CircularProgressBar()
            is LoadState.Error ->
                ErrorScreen(R.string.error_retrieving_characters) { onRefresh() }
            else ->
                CharacterListSuccessContent(
                    items = { items() },
                    onClick = { c -> onClick(c) },
                    addToFavorites = { c -> addToFavorites(c) },
                )
        }
    }

    if (items().loadState.source.refresh is LoadState.Loading || items().loadState.append is LoadState.Loading || items().loadState.refresh is LoadState.Loading) {
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
    items: () -> LazyPagingItems<CharacterUI>,
    onClick: (CharacterUI) -> Unit,
    addToFavorites: (CharacterUI) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()
    val showScrollToTopButton = remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex > 0
        }
    }
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
        state = lazyGridState,
    ) {
        gridItems(
            items = items(),
            key = {
                it.id
            }, itemContent = { domainCharacter ->
                domainCharacter
                    ?.let { characterUI ->
                        CharacterItemListContent(
                            characterProvider = { characterUI },
                            onClick = { c -> onClick(c) },
                            addToFavorites = { c -> addToFavorites(c) }
                        )
                    }
            }
        )
    }
    ScrollToTopButton(
        showButton = { showScrollToTopButton.value },
        scrollUp = {
            scope.launch {
                lazyGridState.scrollToItem(0)
            }
        }
    )
}

@Composable
private fun ScrollToTopButton(
    showButton: () -> Boolean,
    scrollUp: () -> Unit,
) {

    AnimatedVisibility(visible = showButton(), enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Button(
                onClick = { scrollUp() },
            ) {
                Text(text = stringResource(id = R.string.scroll_to_top), style = MaterialTheme.typography.labelMedium)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemListContent(
    characterProvider: () -> CharacterUI,
    onClick: (CharacterUI) -> Unit,
    addToFavorites: (CharacterUI) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.character_list_padding))
            .shadow(
                elevation = dimensionResource(id = R.dimen.card_elevation),
                shape = CutCornerShape(dimensionResource(id = R.dimen.corner_shape)
                ),
                spotColor = MaterialTheme.colorScheme.primary)
            .clickable { onClick(characterProvider()) },
        shape = CutCornerShape(size = dimensionResource(id = R.dimen.corner_shape)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = characterProvider().image)
                        .setParameter(characterProvider().name, characterProvider().name)
                        .placeholder(R.drawable.ic_placeholder)
                        .apply {
                            this.size(Size.ORIGINAL)
                        }
                        .error(R.drawable.ic_placeholder).build()
                )
                Image(
                    painter = painter,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = characterProvider().image,
                    contentScale = ContentScale.FillWidth,
                )
                FavoriteButton(
                    isFavorite = { characterProvider().isFavorite },
                    action = {
                        addToFavorites(characterProvider().copy(isFavorite = !characterProvider().isFavorite))
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            Text(text = characterProvider().name,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.text),
                    start = dimensionResource(id = R.dimen.text),
                    end = dimensionResource(id = R.dimen.text),
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacer_bottom)))
        }
    }
}
