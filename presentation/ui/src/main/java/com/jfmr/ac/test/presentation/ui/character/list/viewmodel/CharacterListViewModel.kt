package com.jfmr.ac.test.presentation.ui.character.list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jfmr.ac.test.domain.usecase.di.RetrieveItemsQualifier
import com.jfmr.ac.test.domain.usecase.open.character.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    @RetrieveItemsQualifier private val charactersUseCase: CharactersUseCase,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var pager =
            charactersUseCase.characters()
            .cachedIn(viewModelScope)
}
