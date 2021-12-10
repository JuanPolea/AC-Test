package com.jfmr.ac.test.data.repository.open.api.rickandmorty

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): Flow<Characters>
}