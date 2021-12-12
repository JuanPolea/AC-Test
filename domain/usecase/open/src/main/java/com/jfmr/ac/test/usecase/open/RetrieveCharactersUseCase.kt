package com.jfmr.ac.test.usecase.open

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainError

interface RetrieveCharactersUseCase {
    suspend fun invoke(success: (Characters) -> Unit, error: (DomainError) -> Unit)
}