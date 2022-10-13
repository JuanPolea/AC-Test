package com.jfmr.ac.test.domain.usecase.open

import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError

interface CharacterDetailUseCase {
    suspend fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit,
        error: (DomainError) -> Unit,
    )
}
