package com.jfmr.ac.test.data.remote.character.datasource

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import retrofit2.Response

interface CharacterRemoteDataSource {
    suspend fun retrieveCharacterById(characterId: Int): Response<CharacterResponse>
    suspend fun retrieveCharacters(page: Int): CharactersResponse
    fun getNetworkService(): RickAndMortyApiService
}
