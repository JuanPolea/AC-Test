package com.jfmr.ac.test.presentation.ui.character.detail.model.mapper

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState

object CharacterDetailMapper {

    internal fun CharacterDetail.toCharacterStateSuccess() =
        CharacterDetailState.Success(this)

    internal fun DomainError.toCharacterStateError() =
        CharacterDetailState.Error(this)

    internal fun toCharacterStateLoading(messageResource: Int) = CharacterDetailState.Loading(messageResource)

}
