package com.jfmr.ac.test.usecase.open

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface RetrieveCharactersUseCase {
    suspend fun invoke(): Flow<Characters>
}