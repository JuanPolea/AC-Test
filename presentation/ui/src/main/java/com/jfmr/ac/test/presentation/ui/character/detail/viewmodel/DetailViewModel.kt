package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.model.mapper.CharacterDetailMapper
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.di.CharacterDetail
import com.jfmr.ac.test.usecase.di.UpdateCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @CharacterDetail private val characterDetailUseCase: CharacterDetailUseCase,
    @UpdateCharacter private val updateCharacterUseCase: UpdateCharacterUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var characterDetailState by mutableStateOf<CharacterDetailState>(CharacterDetailMapper.toCharacterStateLoading(R.string.retrieving_characters))
        private set

    init {
        savedStateHandle
            .get<String>("characterId")
            ?.toInt()
            ?.let {
                viewModelScope.launch {
                    characterDetailUseCase.invoke(
                        it,
                        ::success,
                        ::error,
                    )
                }
            } ?: error()

    }

    fun onCharacterDetailEvent(characterDetailEvent: CharacterDetailEvent) {
        characterDetailState = when (characterDetailEvent) {
            is CharacterDetailEvent.CharacterFound -> {
                CharacterDetailState.Success(characterDetailEvent.character)
            }
            CharacterDetailEvent.CharacterNotFound -> CharacterDetailState.Error(CharacterDetailError.CharacterNotFound)
            CharacterDetailEvent.CharacterServerError -> CharacterDetailState.Error(CharacterDetailError.ServerError)
        }
    }

    private fun success(character: Character) = onCharacterDetailEvent(CharacterDetailEvent.CharacterFound(character))

    private fun error() = onCharacterDetailEvent(CharacterDetailEvent.CharacterServerError)

    internal fun updateCharacter(character: Character) {
        viewModelScope.launch {
            characterDetailState = CharacterDetailState.Success(updateCharacterUseCase.updateCharacter(character))
        }
    }
}
