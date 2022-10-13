package com.jfmr.ac.test.domain.usecase.open

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.error.DomainError

interface RetrieveCharactersUseCase {
    suspend fun invoke(success: (Characters) -> Unit, error: (DomainError) -> Unit)
}
