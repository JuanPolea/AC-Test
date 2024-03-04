package com.jfmr.ac.test.data.remote.character.datasource

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI

interface CharacterRemoteDataSource {
    suspend fun retrieveCharacterById(characterId: Int): Result<CharacterResponse>
    suspend fun retrieveCharacters(page: Int): Result<CharactersResponse>
    fun getNetworkService(): RickAndMortyAPI
}
