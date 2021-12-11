package com.jfmr.ac.test.data.repository.open.api.rickandmorty

import com.jfmr.ac.test.domain.model.Characters


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): Characters?
}