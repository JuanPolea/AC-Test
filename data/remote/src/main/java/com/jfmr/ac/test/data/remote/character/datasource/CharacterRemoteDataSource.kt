package com.jfmr.ac.test.data.remote.character.datasource

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CharacterRemoteDataSource {
    suspend fun retrieveCharacterById(characterId: Int): Response<CharacterResponse>
    suspend fun retrieveCharacters(page: Int): CharactersResponse
    fun getNetworkService(): RickAndMortyApiService
}
