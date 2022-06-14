package com.jfmr.ac.test.presentation.ui.character.detail.model

import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.R

sealed interface CharacterDetailState {
    data class Loading(val messageResource: Int = R.string.loading) : CharacterDetailState
    data class Success(val characterDetail: CharacterDetail) : CharacterDetailState
    data class Error(val domainError: DomainError) : CharacterDetailState

}

