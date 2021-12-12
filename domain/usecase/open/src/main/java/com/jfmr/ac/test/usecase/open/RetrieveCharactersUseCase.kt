package com.jfmr.ac.test.usecase.open

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainResult

interface RetrieveCharactersUseCase {
    suspend fun invoke(): DomainResult<Characters?>
}