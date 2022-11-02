package com.jfmr.ac.test.domain.usecase.open.character

import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.error.DomainError

interface CharacterDetailUseCase {
    suspend fun invoke(
        characterId: Int,
        success: (DomainCharacter) -> Unit,
        error: (DomainError) -> Unit,
    )
}
