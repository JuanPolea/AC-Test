package com.jfmr.ac.test.presentation.ui.character.list.model

import com.jfmr.ac.test.domain.model.character.DomainCharacters
import com.jfmr.ac.test.domain.model.error.DomainError

sealed class CharacterListState {
    object Initial : CharacterListState()
    data class Success(val characters: DomainCharacters) : CharacterListState()
    data class Error(val error: DomainError) : CharacterListState()
}
