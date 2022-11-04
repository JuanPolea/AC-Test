package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.usecase.di.CharacterDetail
import com.jfmr.ac.test.domain.usecase.di.UpdateCharacter
import com.jfmr.ac.test.domain.usecase.open.character.CharacterDetailUseCase
import com.jfmr.ac.test.domain.usecase.open.character.UpdateCharacterUseCase
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.model.mapper.CharacterDetailMapper
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

    lateinit var characterDetail: DomainCharacter

    init {
        savedStateHandle.get<String>("characterId")?.toInt()?.let {
            characterDetail = DomainCharacter(id = it)
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
                characterDetail = characterDetailEvent.character
                CharacterDetailState.Success(characterDetail)
            }
            CharacterDetailEvent.CharacterNotFound -> CharacterDetailState.Error(CharacterDetailError.CharacterNotFound)
            CharacterDetailEvent.CharacterServerError -> CharacterDetailState.Error(CharacterDetailError.ServerError)
        }
    }

    private fun success(character: DomainCharacter) = onCharacterDetailEvent(CharacterDetailEvent.CharacterFound(character))

    private fun error() = onCharacterDetailEvent(CharacterDetailEvent.CharacterServerError)

    internal fun updateCharacter(character: DomainCharacter) {
        viewModelScope.launch {
            characterDetailState = CharacterDetailState.Success(updateCharacterUseCase.updateCharacter(character))
        }
    }
}
