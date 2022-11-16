package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailUI
import com.jfmr.ac.test.presentation.ui.character.detail.model.toDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.toUI
import com.jfmr.ac.test.presentation.ui.character.list.model.toDomain
import com.jfmr.ac.test.presentation.ui.character.list.model.toUI
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.di.CharacterDetailUC
import com.jfmr.ac.test.usecase.di.UpdateCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    @CharacterDetailUC private val characterDetailUseCase: CharacterDetailUseCase,
    @UpdateCharacter private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _characterDetailUIState = MutableStateFlow(CharacterDetailUI())
    val characterDetailState: MutableStateFlow<CharacterDetailUI> = _characterDetailUIState
    private val trigger = MutableStateFlow("")
    private val charactersFlow: Flow<Unit> =
        trigger.mapLatest {
            savedStateHandle
                .get<String>("characterId")
                ?.toInt()
                ?.let {
                    characterDetailUseCase.invoke(
                        it,
                        ::success,
                        ::error,
                    )
                } ?: error()
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    internal fun getData() {
        viewModelScope.launch {
            charactersFlow
                .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
                .collect()
        }
    }

    private fun success(characterDetail: com.jfmr.ac.test.domain.model.character.CharacterDetail) {
        onEvent(CharacterDetailEvent.CharacterFound(characterDetail.toUI()))
    }

    private fun error() = onEvent(CharacterDetailEvent.CharacterNotFound)

    fun onEvent(characterDetailEvent: CharacterDetailEvent) {
        when (characterDetailEvent) {
            is CharacterDetailEvent.CharacterFound -> {
                characterDetailState.update { cd ->
                    cd.copy(
                        character = characterDetailEvent.detailUI.character,
                        episodes = characterDetailEvent.detailUI.episodes,
                        isLoading = false,
                        error = null
                    )
                }
            }
            CharacterDetailEvent.CharacterNotFound -> {
                characterDetailState.update {
                    it.copy(
                        isLoading = false,
                        error = CharacterDetailError.CharacterNotFound
                    )
                }
            }
            CharacterDetailEvent.CharacterServerError -> {
                characterDetailState.update {
                    it.copy(
                        isLoading = false,
                        error = CharacterDetailError.ServerError
                    )
                }
            }
            is CharacterDetailEvent.UpdateCharacter ->
                viewModelScope.launch {
                    characterDetailEvent
                        .detailUI
                        .character
                        .toDomain()
                        .let {
                            updateCharacterUseCase(it).collectLatest { character ->
                                characterDetailState.update { cd ->
                                    cd.copy(
                                        character = character.toUI(),
                                        error = null
                                    )
                                }
                            }

                        }
                }
            is CharacterDetailEvent.EpisodesFound -> {
                characterDetailState.update {
                    it.copy(
                        episodes = characterDetailEvent.detail.episodes,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun error(domainError: DomainError) {
        domainError.toDetailError().let {
            val errorEvent = when (it) {
                CharacterDetailError.CharacterNotFound -> CharacterDetailEvent.CharacterNotFound
                CharacterDetailError.ServerError, CharacterDetailError.UnknownError -> CharacterDetailEvent.CharacterServerError
            }
            onEvent(errorEvent)
        }

        characterDetailState.update {
            it.copy(
                episodes = emptyList(),
                error = domainError.toDetailError()
            )
        }
    }
}
