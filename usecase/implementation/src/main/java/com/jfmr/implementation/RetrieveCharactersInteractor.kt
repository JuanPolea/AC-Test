package com.jfmr.implementation

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.open.RetrieveCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(private val retrieveCharactersDataSource: RetrieveCharactersDataSource) :
    RetrieveCharactersUseCase {

    override suspend fun retrieveCharacters(): Flow<Characters> = flow {
        retrieveCharactersDataSource.retrieveCharacters().forEach {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

}