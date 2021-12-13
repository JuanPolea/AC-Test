package com.jfmr.ac.test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.jfmr.ac.test.character.list.model.CharacterListState
import com.jfmr.ac.test.character.list.viewmodel.CharacterListViewModel
import com.jfmr.ac.test.domain.model.ResultsItem
import com.jfmr.ac.test.ui.component.ErrorScreen
import com.jfmr.ac.test.ui.theme.ACTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyApp()
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ItemListScreen(modifier: Modifier) {
    //TODO fix scroll position on orientation changes
    val characterListViewModel: CharacterListViewModel = hiltViewModel()
    val characterListState by characterListViewModel.characterSF.collectAsState()
    when (characterListState) {
        is CharacterListState.Initial -> {}
        is CharacterListState.Error -> ErrorScreen("faksjdlñ")
        is CharacterListState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val listItems: List<ResultsItem> =
                    (characterListState as CharacterListState.Success).characters.results?.filterNotNull() ?: emptyList()
                items(listItems) { item ->
                    CharacterItem(item)
                }
            }
        }
    }
}

@Composable
fun CharacterItem(resultsItem: ResultsItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
            .background(Color.Blue)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
        ) {
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
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun RickAndMortyApp() {
    ACTestTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.app_name)) }
                    )
                }
            ) {
                ItemListScreen(modifier = Modifier.padding(it))
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyApp()
}