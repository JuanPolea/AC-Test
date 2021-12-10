package com.jfmr.data.remote

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

class RickAndMortyRemoteRepository : RetrieveCharactersDataSource {
    override suspend fun retrieveCharacters(): Flow<Characters> {
        TODO("Not yet implemented")
    }


}