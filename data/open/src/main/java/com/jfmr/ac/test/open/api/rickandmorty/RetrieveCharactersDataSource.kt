package com.jfmr.ac.test.open.api.rickandmorty

import com.jfmr.ac.test.domain.model.Characters


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): List<Characters>
}