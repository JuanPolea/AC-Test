package com.jfmr.ac.test.presentation.ui.character.list.model.mapper

import com.jfmr.ac.test.domain.model.DomainCharacters
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListState

fun DomainCharacters.toSuccess() =
    CharacterListState.Success(this)

fun DomainError.toError() =
    CharacterListState.Error(this)
