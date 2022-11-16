package com.jfmr.ac.test.presentation.ui.character.detail.model

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.model.toUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.EpisodeUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.toUI

data class CharacterDetailUI(
    val character: CharacterUI = CharacterUI(),
    val episodes: List<EpisodeUI> = emptyList(),
    val isLoading: Boolean? = true,
    val error: CharacterDetailError? = null,
)

sealed interface CharacterDetailError : DomainError {
    object ServerError : CharacterDetailError
    object CharacterNotFound : CharacterDetailError
    object UnknownError : CharacterDetailError
}

sealed interface CharacterDetailEvent {
    data class CharacterFound(val detailUI: CharacterDetailUI) : CharacterDetailEvent
    data class EpisodesFound(val detail: CharacterDetailUI) : CharacterDetailEvent
    object CharacterNotFound : CharacterDetailEvent
    object CharacterServerError : CharacterDetailEvent
    data class UpdateCharacter(val detailUI: CharacterDetailUI) : CharacterDetailEvent
}

internal fun CharacterDetail.toUI() = CharacterDetailUI(
    character = character?.toUI() ?: CharacterUI(),
    episodes = episodes.toUI(),
)

internal fun DomainError.toDetailError() = when (this) {
    is RemoteError.Server -> CharacterDetailError.ServerError
    is RemoteError.Connectivity -> CharacterDetailError.CharacterNotFound
    else -> CharacterDetailError.UnknownError
}
