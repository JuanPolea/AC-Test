package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState
import com.jfmr.ac.test.presentation.ui.character.detail.model.mapper.CharacterDetailMapper
import com.jfmr.ac.test.usecase.di.CharacterDetailQualifier
import com.jfmr.ac.test.usecase.open.CharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @CharacterDetailQualifier private val characterDetailUseCase: CharacterDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var characterDetailState by mutableStateOf<CharacterDetailState>(CharacterDetailMapper.toCharacterStateLoading(R.string.retrieving_characters))
        private set


    init {
        viewModelScope.launch {
            characterDetailUseCase.invoke(
                0,
                ::success,
                ::error,
            )
        }
    }

    private fun success(characterDetail: CharacterDetail) {
        characterDetailState = CharacterDetailMapper.toCharacterStateSuccess(characterDetail)
    }

    private fun error(domainError: DomainError) =
        CharacterDetailMapper.toCharacterStateError(domainError)
}