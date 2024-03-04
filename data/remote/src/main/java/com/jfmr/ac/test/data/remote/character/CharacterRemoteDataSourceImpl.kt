package com.jfmr.ac.test.data.remote.character

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val ktorService: RickAndMortyAPI,
) : CharacterRemoteDataSource {

    override suspend fun retrieveCharacterById(characterId: Int): Result<CharacterResponse> =
        ktorService.retrieveCharacterById(characterId)


    override suspend fun retrieveCharacters(page: Int): Result<CharactersResponse> =
        ktorService.characters(page)

    override fun getNetworkService() = ktorService
}
