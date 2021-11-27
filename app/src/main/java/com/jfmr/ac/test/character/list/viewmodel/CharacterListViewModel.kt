package com.jfmr.ac.test.character.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.character.list.model.CharacterListState
import com.jfmr.ac.test.open.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {


    val charactersMSF = MutableStateFlow<CharacterListState>(CharacterListState.Initial)
    val charactersSF: StateFlow<CharacterListState> = charactersMSF

    fun retrieveCharacters() {
        viewModelScope.launch {
        }
    }

    init {
        retrieveCharacters()
    }
}