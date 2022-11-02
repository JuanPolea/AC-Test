package com.jfmr.ac.test.presentation.ui.character.detail.model

import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.R

sealed interface CharacterDetailState {
    data class Loading(val messageResource: Int = R.string.loading) : CharacterDetailState
    data class Success(val characterDetail: DomainCharacter) : CharacterDetailState
    data class Error(val domainError: DomainError) : CharacterDetailState

}

sealed interface CharacterDetailError : DomainError {
    object ServerError : CharacterDetailError
    object CharacterNotFound : CharacterDetailError
}

sealed interface CharacterDetailEvent {
    data class CharacterFound(val character: DomainCharacter) : CharacterDetailEvent
    object CharacterNotFound : CharacterDetailEvent
    object CharacterServerError : CharacterDetailEvent
}
