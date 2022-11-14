package com.jfmr.ac.test.presentation.ui.character.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListEvent
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.model.toDomain
import com.jfmr.ac.test.presentation.ui.character.list.model.toUI
import com.jfmr.ac.test.usecase.character.CharactersUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.di.GetCharacters
import com.jfmr.ac.test.usecase.di.UpdateCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    @GetCharacters private val charactersUseCase: CharactersUseCase,
    @UpdateCharacter private val updateCharacterUseCase: UpdateCharacterUseCase,
) : ViewModel() {

    var pager: Flow<PagingData<CharacterUI>> =
        charactersUseCase
            .invoke()
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData
                    .map { character -> character.toUI() }
            }
        private set

    fun onEvent(characterListEvent: CharacterListEvent) {
        when (characterListEvent) {
            is CharacterListEvent.AddToFavorite ->
                viewModelScope.launch {
                    updateCharacterUseCase.invoke(characterListEvent.character.toDomain())
                }
        }
    }
}
