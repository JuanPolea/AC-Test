package com.jfmr.ac.test.presentation.ui.character.detail.model

import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.EpisodeUI

data class CharacterDetail(
    val character: CharacterUI = CharacterUI(),
    val episodes: List<EpisodeUI> = emptyList(),
    val isLoading: Boolean? = false,
    val error: CharacterDetailError? = null,
)

sealed interface CharacterDetailError : DomainError {
    object ServerError : CharacterDetailError
    object CharacterNotFound : CharacterDetailError
    object UnknownError : CharacterDetailError
}

sealed interface CharacterDetailEvent {
    data class CharacterFound(val character: Character) : CharacterDetailEvent
    data class EpisodesFound(val characterDetail: CharacterDetail) : CharacterDetailEvent
    object CharacterNotFound : CharacterDetailEvent
    object CharacterServerError : CharacterDetailEvent
    data class UpdateCharacter(val characterDetail: CharacterDetail) : CharacterDetailEvent
}

fun DomainError.toDetailError() =
    when (this) {
        is RemoteError.Server -> CharacterDetailError.ServerError
        is RemoteError.Connectivity -> CharacterDetailError.CharacterNotFound
        else -> CharacterDetailError.UnknownError
    }
