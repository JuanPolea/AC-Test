package com.jfmr.data.remote

import android.util.Log
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterDetailResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Info
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Location
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.Origin
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.ResultsItem
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.repository.open.mapper.tryCall
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.DomainResult
import javax.inject.Inject
import com.jfmr.ac.test.domain.model.Characters as DomainCharacters
import com.jfmr.ac.test.domain.model.Info as DomainInfo
import com.jfmr.ac.test.domain.model.Location as DomainLocation
import com.jfmr.ac.test.domain.model.Origin as DomainOrigin
import com.jfmr.ac.test.domain.model.ResultsItem as DomainResultItem


class RetrieveRemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService,
) : RetrieveCharactersDataSource {

    override suspend fun retrieveCharacters(): DomainResult<DomainCharacters?> =
        tryCall {
            remoteService
                .retrieveAllCharacters()
                .body()
                ?.toDomain()
        }

    override suspend fun retrieveCharacterDetail(characterId: Int) =
        tryCall {
            val tema = remoteService
                .retrieveCharacterById(characterId)
                .body()
            Log.e("lñkfdjds", tema.toString())

            tema.toDomain()
        }


    private fun CharacterResponse.toDomain() =
        DomainCharacters(
            results = results.toDomain(),
            info = info?.toDomain()
        )

    private fun List<ResultsItem?>?.toDomain() =
        this?.filterNotNull()?.map { it.toDomain() }

    private fun ResultsItem.toDomain() =
        id?.let {
            DomainResultItem(
                image = image,
                gender = gender,
                species = species,
                created = created,
                origin = origin?.toDomain(),
                name = name,
                location = location?.toDomain(),
                episode = episode,
                id = it,
                type = type,
                url = url,
                status = status
            )
        }

    private fun Info.toDomain() =
        DomainInfo(
            next = next,
            pages = pages,
            prev = prev,
            count = count
        )

    private fun Origin?.toDomain() =
        DomainOrigin(
            name = this?.name.orEmpty(),
            url = this?.url.orEmpty(),
        )

    private fun Location?.toDomain() =
        DomainLocation(
            name = this?.name,
            url = this?.url,
        )

    private fun CharacterDetailResponse?.toDomain() =
        CharacterDetail(
            image = this?.image.orEmpty(),
            gender = this?.gender.orEmpty(),
            species = this?.species.orEmpty(),
            created = this?.created.orEmpty(),
            origin = this?.origin?.toDomain(),
            name = this?.name.orEmpty(),
            location = this?.location?.toDomain(),
            episode = this?.episode.orEmpty() as List<String>,
            id = this?.id ?: -1,
            type = this?.type.orEmpty(),
            url = this?.url.orEmpty(),
            status = this?.status.orEmpty()
        )

}
