package com.jfmr.ac.test.data.api.rickandmorty.character.datasource

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult

interface RemoteDataSource {
    suspend fun retrieveCharacterById(characterId: Int): DomainResult<CharacterResponse>
    fun getNetworkService(): RickAndMortyApiService
}
