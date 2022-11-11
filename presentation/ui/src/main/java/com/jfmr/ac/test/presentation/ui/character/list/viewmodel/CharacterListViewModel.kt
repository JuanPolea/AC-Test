package com.jfmr.ac.test.presentation.ui.character.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.usecase.character.CharactersUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.di.GetCharacters
import com.jfmr.ac.test.usecase.di.UpdateCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    @GetCharacters private val charactersUseCase: CharactersUseCase,
    @UpdateCharacter private val updateCharacterUseCase: UpdateCharacterUseCase,
) : ViewModel() {

    var pager: Flow<PagingData<Character>> = charactersUseCase.characters().cachedIn(viewModelScope)

    fun addToFavorite(it: Character) {
        viewModelScope.launch {
            updateCharacterUseCase.updateCharacter(it)
        }

    }
}
