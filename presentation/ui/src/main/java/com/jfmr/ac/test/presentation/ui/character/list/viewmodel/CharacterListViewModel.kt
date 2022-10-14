package com.jfmr.ac.test.presentation.ui.character.list.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jfmr.ac.test.domain.usecase.di.RetrieveItemsQualifier
import com.jfmr.ac.test.domain.usecase.open.RetrieveCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    @RetrieveItemsQualifier private val retrieveCharactersUseCase: RetrieveCharactersUseCase,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var charLoading: Boolean by mutableStateOf(true)

    var pager =
        Pager(PagingConfig(50)) {
            retrieveCharactersUseCase.characters()
        }
            .flow
            .cachedIn(viewModelScope)
}
