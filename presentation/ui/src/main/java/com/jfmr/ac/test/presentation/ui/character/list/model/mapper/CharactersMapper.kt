package com.jfmr.ac.test.presentation.ui.character.list.model.mapper

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListState

fun Characters.toSuccess() =
    CharacterListState.Success(this)

fun DomainError.toError() =
    CharacterListState.Error(this)