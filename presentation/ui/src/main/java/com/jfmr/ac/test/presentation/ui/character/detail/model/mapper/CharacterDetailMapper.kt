package com.jfmr.ac.test.presentation.ui.character.detail.model.mapper

import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState

object CharacterDetailMapper {

    internal fun toCharacterStateSuccess(characterDetail: CharacterDetail) =
        CharacterDetailState.Success(characterDetail)

    internal fun toCharacterStateError(domainError: DomainError) =
        CharacterDetailState.Error(domainError)

    internal fun toCharacterStateLoading(messageResource: Int) = CharacterDetailState.Loading(messageResource)

}
