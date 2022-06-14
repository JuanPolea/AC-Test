package com.jfmr.ac.test.presentation.ui.character.detail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.viewmodel.DetailViewModel
import com.jfmr.ac.test.presentation.ui.main.component.CircularProgressBar
import com.jfmr.ac.test.presentation.ui.main.component.ErrorScreen

@Composable
fun CharacterDetailScreen(
    itemId: String,
    onUpClick: () -> Unit,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detail")
                },
                navigationIcon = {
                    IconButton(onClick = { onUpClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        DetailContent()
    }

}

@Composable
internal fun DetailContent(detailViewModel: DetailViewModel = hiltViewModel()) {

    when (val detailViewState = detailViewModel.characterDetailState) {
        is CharacterDetailState.Loading ->
            CircularProgressBar(stringResource(id = detailViewState.messageResource))
        is CharacterDetailState.Error ->
            ErrorScreen(text = stringResource(id = R.string.character_detail_not_found))
        is CharacterDetailState.Success ->
            CharacterDetailSuccessContent(detailViewState.characterDetail)
    }


}

@Composable
private fun CharacterDetailSuccessContent(characterDetail: CharacterDetail) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = characterDetail.name)
    }
}