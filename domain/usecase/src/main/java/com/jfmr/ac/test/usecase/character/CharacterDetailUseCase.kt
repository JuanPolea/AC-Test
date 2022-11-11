package com.jfmr.ac.test.usecase.character

import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.DomainError

interface CharacterDetailUseCase {
    suspend fun invoke(
        characterId: Int,
        success: (Character) -> Unit,
        error: (DomainError) -> Unit,
    )
}
