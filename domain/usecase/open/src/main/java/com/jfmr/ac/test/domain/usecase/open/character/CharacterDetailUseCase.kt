package com.jfmr.ac.test.domain.usecase.open.character

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError

interface CharacterDetailUseCase {
    suspend fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit,
        error: (DomainError) -> Unit,
    )
}
