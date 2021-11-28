package com.jfmr.implementation

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor() :
    RetrieveCharactersUseCase {

    override suspend fun retrieveCharacters(): Flow<Characters> = flow {
        emit(Characters())
    }

}