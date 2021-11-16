package com.jfmr.open

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface RetrieveCharactersUseCase {
    suspend fun retrieveCharacters(): Flow<Characters>
}