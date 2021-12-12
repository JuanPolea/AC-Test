package com.jfmr.data.remote

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Info
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.ResultsItem
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.repository.open.error.tryCall
import com.jfmr.ac.test.domain.model.DomainResult
import javax.inject.Inject
import com.jfmr.ac.test.domain.model.Characters as DomainCharacters
import com.jfmr.ac.test.domain.model.Info as InfoDomain
import com.jfmr.ac.test.domain.model.ResultsItem as ResultItemDomain


class RetrieveRemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService
) : RetrieveCharactersDataSource {

    override suspend fun retrieveCharacters(): DomainResult<DomainCharacters?> =
        tryCall {
            val response = remoteService.retrieveAllCharacters()
            response.body()?.toDomain()
        }

    private fun CharacterResponse.toDomain() =
        DomainCharacters(
            results.toDomain(),
            info?.toDomain()
        )

    private fun List<ResultsItem?>?.toDomain() =
        this?.filterNotNull()?.map { it.toDomain() }

    private fun ResultsItem.toDomain() =
        ResultItemDomain(
            image,
            gender,
            species,
            created
        )

    private fun Info.toDomain() =
        InfoDomain(
            next,
            pages,
            prev,
            count
        )
}