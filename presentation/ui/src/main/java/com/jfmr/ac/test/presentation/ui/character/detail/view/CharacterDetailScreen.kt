package com.jfmr.ac.test.presentation.ui.character.detail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.main.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onUpClick: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val detailViewState = detailViewModel.characterDetailState
    Scaffold(topBar = {
        SmallTopAppBar(title = {
            Text(text = "Detail")
        }, navigationIcon = {
            IconButton(onClick = { onUpClick.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
    }) {
        DetailContent(detailViewState)
    }

}

@Composable
internal fun DetailContent(characterDetailState: CharacterDetailState) {

    when (characterDetailState) {
        is CharacterDetailState.Loading -> CircularProgressBar(stringResource(id = characterDetailState.messageResource))
        is CharacterDetailState.Error -> ErrorScreen(text = stringResource(id = R.string.character_detail_not_found))
        is CharacterDetailState.Success -> CharacterDetailSuccessContent(characterDetailState.characterDetail)
    }


}

@Composable
private fun CharacterDetailSuccessContent(characterDetail: CharacterDetail) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                AsyncImage(model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(characterDetail.image)
                    .crossfade(true)
                    .build(),
                    contentDescription = stringResource(id = R.string.image_detail_description),
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop)
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.character_detail_padding),
                            start = dimensionResource(id = R.dimen.character_detail_padding),
                            end = dimensionResource(id = R.dimen.character_list_padding),
                        ),
                ) {
                    Text(
                        text = stringResource(id = R.string.name),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = characterDetail.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.character_detail_padding),
                            end = dimensionResource(id = R.dimen.character_list_padding),
                        ),
                ) {
                    Text(
                        text = stringResource(id = R.string.status),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = characterDetail.status,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.character_detail_padding),
                            start = dimensionResource(id = R.dimen.character_detail_padding),
                            end = dimensionResource(id = R.dimen.character_list_padding),
                        ),
                ) {
                    Text(
                        text = stringResource(id = R.string.location),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = characterDetail.location?.name ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
