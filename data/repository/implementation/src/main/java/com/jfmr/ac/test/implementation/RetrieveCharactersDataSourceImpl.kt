package com.jfmr.ac.test.implementation

import com.jfmr.ac.test.data.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.ac.test.domain.model.Characters

class RetrieveCharactersDataSourceImpl() : RetrieveCharactersDataSource {
    override suspend fun retrieveCharacters(): List<Characters> {
        return emptyList()
    }

}