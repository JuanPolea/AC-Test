package com.jfmr.ac.test.presentation.ui.character.list.view

import android.os.Parcel
import android.os.Parcelable
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
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.presentation.ui.main.component.MainAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    onClick: (DomainCharacter) -> Unit,
) {
    val state = rememberLazyGridState()
    val lazyPagingItems = characterListViewModel.pager.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            MainAppBar()
        },
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding), contentAlignment = Alignment.Center) {
            when (lazyPagingItems.itemCount) {
                0 -> CircularProgressIndicator()
                else -> {
                    CharacterListContent(
                        modifier = modifier,
                        state = state,
                        onClick = onClick,
                        items = lazyPagingItems,
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterListContent(
    modifier: Modifier,
    state: LazyGridState,
    onClick: (DomainCharacter) -> Unit,
    items: LazyPagingItems<DomainCharacter>,
) {
    val showScrollToTopButton = remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 0
        }
    }
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.adaptative_size)),
        state = state,
    ) {
        gridItems(
            items = items,
            key = {
                it.id
            },
            itemContent = { domainCharacter ->
                if (domainCharacter != null)
                    CharacterItemListContent(domainCharacter = { domainCharacter }, onClick)
            }
        )
    }

    ScrollToTopButton(showScrollToTopButton, state)
}

@Composable
private fun ScrollToTopButton(
    showButton: State<Boolean>,
    state: LazyGridState,
) {
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = showButton.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        )
        {
            Button(
                onClick = {
                    scope.launch {
                        state.animateScrollToItem(0)
                    }
                },
            ) {
                Text(
                    text = stringResource(id = R.string.scroll_to_top),
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItemListContent(
    domainCharacter: () -> DomainCharacter,
    onClick: (DomainCharacter) -> Unit,
    modifier: Modifier = Modifier,
) {

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
            Column {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = domainCharacter().image)
                            .placeholder(R.drawable.ic_placeholder)
                            .crossfade(true)
                            .apply(
                                block = fun ImageRequest.Builder.() {
                                    size(Size.ORIGINAL)
                                }
                            )
                            .build()
                    ),
                    modifier = modifier.fillMaxWidth(),
                    contentDescription = domainCharacter().image,
                    contentScale = ContentScale.FillWidth,
                )
                domainCharacter().name.let {
                    Text(
                        text = it ?: stringResource(id = R.string.unknow),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.text)),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
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
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}
