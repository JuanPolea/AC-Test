package com.jfmr.ac.test.data.open.api.rickandmorty

import com.jfmr.ac.test.domain.model.Characters


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): List<Characters>
}