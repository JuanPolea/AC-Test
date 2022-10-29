package com.jfmr.ac.test.data.open.rickandmorty.character.datasource

import androidx.paging.PagingData
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainResult
import kotlinx.coroutines.flow.Flow


interface CharactersDataSource {
    fun fetchCharacters(): Flow<PagingData<DomainCharacter>>
    suspend fun retrieveCharacterDetail(characterId: Int): DomainResult<CharacterDetail>
}
