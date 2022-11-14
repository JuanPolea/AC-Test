package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetail
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.toDetailError
import com.jfmr.ac.test.presentation.ui.character.list.model.toDomain
import com.jfmr.ac.test.presentation.ui.character.list.model.toUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.toUI
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.di.CharacterDetailUC
import com.jfmr.ac.test.usecase.di.GetEpisode
import com.jfmr.ac.test.usecase.di.UpdateCharacter
import com.jfmr.ac.test.usecase.episode.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @CharacterDetailUC private val characterDetailUseCase: CharacterDetailUseCase,
    @UpdateCharacter private val updateCharacterUseCase: UpdateCharacterUseCase,
    @GetEpisode private val getEpisodesUseCase: GetEpisodesUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _characterDetailState = MutableStateFlow(CharacterDetail())
    val characterDetailState: MutableStateFlow<CharacterDetail> = _characterDetailState

    init {
        characterDetailState.update {
            it.copy(isLoading = true)
        }
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

    private fun success(character: Character) =
        onEvent(CharacterDetailEvent.CharacterFound(character))

    private fun error() = onEvent(CharacterDetailEvent.CharacterServerError)

    fun onEvent(characterDetailEvent: CharacterDetailEvent) {
        when (characterDetailEvent) {
            is CharacterDetailEvent.CharacterFound -> {
                characterDetailState.update {
                    it.copy(
                        character = characterDetailEvent.character.toUI(),
                        isLoading = false,
                        error = null
                    )
                }
                episodes(characterDetailEvent.character.episode)
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
                        .characterDetail
                        .character
                        ?.toDomain()
                        ?.let {
                            updateCharacterUseCase(it)
                                .let { c ->
                                    characterDetailState.update { cd ->
                                        cd.copy(
                                            character = c.toUI(),
                                            error = null
                                        )
                                    }
                                }
                        }
                }
            is CharacterDetailEvent.EpisodesFound -> {
                characterDetailState.update {
                    it.copy(
                        episodes = characterDetailEvent.characterDetail.episodes,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun episodes(episodesList: List<String>) = viewModelScope.launch {
        getEpisodesUseCase.invoke(episodesList = episodesList, success = ::success, error = ::error)
    }

    private fun success(episodes: List<Episode>?) = episodes?.let { list ->
        characterDetailState.update {
            it.copy(
                episodes = list.toUI(),
                error = null
            )
        }
    }

    private fun error(domainError: DomainError) {
        Timber.i(domainError.toString())
        characterDetailState.update {
            it.copy(
                episodes = emptyList(),
                error = domainError.toDetailError()
            )
        }
    }
}
