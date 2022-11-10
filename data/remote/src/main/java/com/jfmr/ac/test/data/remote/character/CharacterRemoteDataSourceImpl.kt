package com.jfmr.ac.test.data.remote.character

import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val remoteService: RickAndMortyApiService,
) : CharacterRemoteDataSource {

    override suspend fun retrieveCharacterById(characterId: Int): Response<CharacterResponse> =
            remoteService.retrieveCharacterById(characterId)

    override suspend fun retrieveCharacters(page: Int): CharactersResponse =
        remoteService.characters(page)

    override fun getNetworkService() = remoteService
}
