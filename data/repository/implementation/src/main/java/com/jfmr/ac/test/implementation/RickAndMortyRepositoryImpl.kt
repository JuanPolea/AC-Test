package com.jfmr.ac.test.implementation

import com.jfmr.ac.test.data.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.open.remote.RickAndMortyRepository
import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val retrieveCharactersDataSource: RetrieveCharactersDataSource,
) : RickAndMortyRepository {

    override fun retrieveCharacters(): Flow<Characters> = flow {
        val characters = Characters()
        emit(characters)
    }.flowOn(Dispatchers.IO)

}