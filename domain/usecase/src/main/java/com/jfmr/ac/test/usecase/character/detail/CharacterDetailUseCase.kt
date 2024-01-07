package com.jfmr.ac.test.usecase.character.detail

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError

interface CharacterDetailUseCase {
    suspend operator fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit,
        error: (DomainError) -> Unit,
    )
}
