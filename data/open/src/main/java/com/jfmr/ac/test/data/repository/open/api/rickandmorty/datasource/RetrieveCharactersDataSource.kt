package com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource

import arrow.core.Either
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.RemoteError
import com.jfmr.ac.test.domain.model.Characters


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): Either<RemoteError, Characters?>
}