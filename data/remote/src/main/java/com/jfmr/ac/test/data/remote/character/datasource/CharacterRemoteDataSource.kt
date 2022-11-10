package com.jfmr.ac.test.data.remote.character.datasource

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult

interface CharacterRemoteDataSource {
    suspend fun retrieveCharacterById(characterId: Int): DomainResult<CharacterResponse>
    fun getNetworkService(): RickAndMortyApiService
}
