package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.usecase.di.CharacterDetailQualifier
import com.jfmr.ac.test.domain.usecase.open.CharacterDetailUseCase
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
    @CharacterDetailQualifier private val characterDetailUseCase: CharacterDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var characterDetailState by mutableStateOf<CharacterDetailState>(
        CharacterDetailMapper.toCharacterStateLoading(R.string.retrieving_characters)
    )
        private set


    init {
        savedStateHandle.get<String>("characterId")?.let {
            viewModelScope.launch {
                characterDetailUseCase.invoke(
                    it.toInt(),
                    ::success,
                    ::error,
                )
            }
        } ?: error("Character not Found")

    }

    fun onCharacterDetailEvent(characterDetailEvent: CharacterDetailEvent) {
        characterDetailState = when (characterDetailEvent) {
            is CharacterDetailEvent.CharacterFound ->
                CharacterDetailState.Success(characterDetailEvent.characterDetail)
            CharacterDetailEvent.CharacterNotFound ->
                CharacterDetailState.Error(CharacterDetailError.CharacterNotFound)
            CharacterDetailEvent.CharacterServerError ->
                CharacterDetailState.Error(CharacterDetailError.ServerError)
        }
    }

    private fun success(characterDetail: CharacterDetail) =
        onCharacterDetailEvent(CharacterDetailEvent.CharacterFound(characterDetail))


    private fun error(domainError: DomainError) =
        onCharacterDetailEvent(CharacterDetailEvent.CharacterServerError)


}
