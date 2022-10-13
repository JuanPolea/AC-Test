package com.jfmr.ac.test.data.open.rickandmorty.datasource

import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainResult


interface RetrieveCharactersDataSource {
    suspend fun retrieveCharacters(): DomainResult<Characters?>
    suspend fun retrieveCharacterDetail(characterId: Int): DomainResult<CharacterDetail>
}
