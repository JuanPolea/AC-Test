package com.jfmr.ac.test.presentation.ui.character.list.view

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.main.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (DomainCharacter) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val lazyPagingItems: LazyPagingItems<DomainCharacter> = characterListViewModel.pager.collectAsLazyPagingItems()

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
    onClick: (DomainCharacter) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: () -> Boolean,
    items: () -> LazyPagingItems<DomainCharacter>,
    addToFavorites: (DomainCharacter) -> Unit,
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
    modifier: Modifier,
    state: () -> LazyGridState,
    items: () -> LazyPagingItems<DomainCharacter>,
    onClick: (DomainCharacter) -> Unit,
    showScrollToTopButton: () -> State<Boolean>,
    addToFavorites: (DomainCharacter) -> Unit,
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
                CharacterItemListContent(domainCharacter = { it }, onClick = onClick, addToFavorites = addToFavorites)
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
    domainCharacter: () -> DomainCharacter,
    onClick: (DomainCharacter) -> Unit,
    addToFavorites: (DomainCharacter) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()
    val scale = remember {
        androidx.compose.animation.core.Animatable(1f)
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.character_list_padding))
            .clickable { onClick(domainCharacter()) },
        shape = CutCornerShape(size = 12.dp),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
        ) {
            Column(modifier = modifier) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = domainCharacter().image)
                            .placeholder(R.drawable.ic_placeholder).crossfade(true).apply(block = fun ImageRequest.Builder.() {
                                size(Size.ORIGINAL)
                            }).error(R.drawable.ic_placeholder).build()),
                        modifier = modifier.fillMaxWidth(),
                        contentDescription = domainCharacter().image,
                        contentScale = ContentScale.FillWidth,
                    )
                    Icon(
                        imageVector = if (domainCharacter().isFavorite == true) {
                            Icons.Outlined.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = stringResource(id = R.string.fav_description),
                        tint = Color.Red,
                        modifier = modifier
                            .scale(scale = scale.value)
                            .size(size = dimensionResource(id = R.dimen.favorite_size))
                            .align(Alignment.TopEnd)
                            .padding(dimensionResource(id = R.dimen.character_list_padding))
                            .clickable(interactionSource = interactionSource, indication = null) {
                                coroutineScope.launch {
                                    scale.animateTo(
                                        0.8f,
                                        animationSpec = tween(100),
                                    )
                                    scale.animateTo(
                                        1f,
                                        animationSpec = tween(100),
                                    )
                                }
                                addToFavorites(domainCharacter().copy(isFavorite = !domainCharacter().isFavorite!!))
                            }
                    )
                }
                Text(text = domainCharacter().name ?: stringResource(id = R.string.unknow),
                    modifier = Modifier.padding(
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
}

fun <T : Any> LazyGridScope.gridItems(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit,
) {
    items(count = items.itemCount, key = if (key != null) { index ->
        items[index]?.let(key) ?: PagingPlaceholderKey(index)
    } else {
        null
    }) { index ->
        itemContent(items[index])
    }
}

private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> = object : Parcelable.Creator<PagingPlaceholderKey> {
            override fun createFromParcel(parcel: Parcel) = PagingPlaceholderKey(parcel.readInt())

            override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
        }
    }
}
