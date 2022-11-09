package com.jfmr.ac.test.data.remote.character

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.api.rickandmorty.character.datasource.RemoteDataSource
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.extensions.tryCall
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val remoteService: RickAndMortyApiService,
) : RemoteDataSource {

    override suspend fun retrieveCharacterById(characterId: Int): DomainResult<CharacterResponse> =
        tryCall {
            remoteService.retrieveCharacterById(characterId)
        }.fold(
            {
                it.left()
            }, { response ->
                if (response.isSuccessful) {
                    response.body()?.right() ?: RemoteError.Connectivity.left()
                } else {
                    RemoteError.Server(response.code()).left()
                }
            }
        )

    override fun getNetworkService() = remoteService
}
