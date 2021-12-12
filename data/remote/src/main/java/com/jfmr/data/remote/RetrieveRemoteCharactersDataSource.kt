package com.jfmr.data.remote

import arrow.core.Either
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.RemoteError
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.data.remote.model.CharacterResponse
import com.jfmr.data.remote.model.Info
import com.jfmr.data.remote.model.ResultsItem
import javax.inject.Inject
import com.jfmr.ac.test.domain.model.Characters as DomainCharacters
import com.jfmr.ac.test.domain.model.Info as InfoDomain
import com.jfmr.ac.test.domain.model.ResultsItem as ResultItemDomain


class RetrieveRemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService
) : RetrieveCharactersDataSource {

    override suspend fun retrieveCharacters(): Either<RemoteError, DomainCharacters?> {
        val response = remoteService.retrieveAllCharacters()
        return if (response.isSuccessful) {
            Either.right(response.body()?.toDomain())
        } else
            Either.left(RemoteError.Unknown("jarls"))
    }

    fun CharacterResponse.toDomain() =
        DomainCharacters(
            results.toDomain(),
            info?.toDomain()
        )

    fun List<ResultsItem?>?.toDomain() =
        this?.filterNotNull()?.map { it.toDomain() }

    fun ResultsItem.toDomain() =
        ResultItemDomain(
            image,
            gender,
            species,
            created
        )

    fun Info.toDomain() =
        InfoDomain(
            next,
            pages,
            prev,
            count
        )
}