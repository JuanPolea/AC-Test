package com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainResult


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): DomainResult<Characters?>
}