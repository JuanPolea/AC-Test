package com.jfmr.ac.test.character.list.model.mapper

import com.jfmr.ac.test.character.list.model.CharacterListState
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainError

fun Characters.toSuccess() =
    CharacterListState.Success(this)

fun DomainError.toError() =
    CharacterListState.Error(this)