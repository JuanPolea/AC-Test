package com.jfmr.ac.test.character.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.character.list.model.CharacterListState
import com.jfmr.ac.test.usecase.di.RetrieveItemsQualifier
import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    @RetrieveItemsQualifier private val retrieveCharactersUseCase: RetrieveCharactersUseCase
) : ViewModel() {


    private val characterMSF = MutableStateFlow<CharacterListState>(CharacterListState.Initial)
    internal val characterSF: StateFlow<CharacterListState> = characterMSF

    private fun retrieveCharacters() {
        viewModelScope.launch {
            retrieveCharactersUseCase.invoke()
        }
    }

    init {
        retrieveCharacters()
    }
}