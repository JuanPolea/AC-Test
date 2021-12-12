package com.jfmr.ac.test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfmr.ac.test.character.list.viewmodel.CharacterListViewModel
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
    val characterListViewModel: CharacterListViewModel = hiltViewModel()
    characterListViewModel.characterSF.collectAsState()
    LazyVerticalGrid(
        cells = GridCells.Adaptive(2.dp),
        contentPadding = PaddingValues(all = 150.dp),
        modifier = modifier
    ) {

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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyApp()
}