package com.jfmr.data.remote

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Info
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Location
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Origin
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.ResultsItem
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.repository.open.mapper.tryCall
import com.jfmr.ac.test.domain.model.DomainResult
import javax.inject.Inject
import com.jfmr.ac.test.domain.model.Characters as DomainCharacters
import com.jfmr.ac.test.domain.model.Info as DomainInfo
import com.jfmr.ac.test.domain.model.Location as DomainLocation
import com.jfmr.ac.test.domain.model.Origin as DomainOrigin
import com.jfmr.ac.test.domain.model.ResultsItem as DomainResultItem


class RetrieveRemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService
) : RetrieveCharactersDataSource {

    override suspend fun retrieveCharacters(): DomainResult<DomainCharacters?> =
        tryCall {
            remoteService
                .retrieveAllCharacters()
                .body()
                ?.toDomain()
        }

    private fun CharacterResponse.toDomain() =
        DomainCharacters(
            results.toDomain(),
            info?.toDomain()
        )

    private fun List<ResultsItem?>?.toDomain() =
        this?.filterNotNull()?.map { it.toDomain() }

    private fun ResultsItem.toDomain() =
        id?.let {
            DomainResultItem(
                image,
                gender,
                species,
                created,
                origin?.toDomain(),
                name,
                location?.toDomain(),
                episode,
                it,
                type,
                url,
                status
            )
        }

    private fun Info.toDomain() =
        DomainInfo(
            next,
            pages,
            prev,
            count
        )

    private fun Origin.toDomain() =
        DomainOrigin(
            name, url
        )

    private fun Location.toDomain() =
        DomainLocation(
            name, url
        )
}