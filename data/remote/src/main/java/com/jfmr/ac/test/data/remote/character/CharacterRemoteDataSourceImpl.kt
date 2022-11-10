package com.jfmr.ac.test.data.remote.character

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharactersResponse
import com.jfmr.ac.test.data.remote.character.extensions.tryCall
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import kotlinx.coroutines.flow.Flow
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
