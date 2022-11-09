package com.jfmr.ac.test.data.repository.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.api.rickandmorty.character.datasource.RemoteDataSource
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.entities.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.LocalLocation
import com.jfmr.ac.test.data.cache.entities.LocalOrigin
import com.jfmr.ac.test.data.paging.RickAndMortyRemoteMediator
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class CharacterRepositoryImpl
@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun characters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RickAndMortyRemoteMediator(localCharacterDataSource.geLocalDB(), remoteDataSource.getNetworkService()),
            pagingSourceFactory = { localCharacterDataSource.getCharacters() }
        ).flow
            .mapLatest { paging ->
                paging.map { localCharacter ->
                    localCharacter.toDomain()
                }
            }
    }

    override suspend fun getCharacterById(id: Int): DomainResult<Character> =
        remoteDataSource
            .retrieveCharacterById(id)
            .fold(
                { error ->
                    localCharacterDataSource
                        .getCharacterById(id)
                        ?.let { localCharacter ->
                            localCharacter.toDomain().right()
                        } ?: error.left()
                },
                { characterResponse ->
                    val localCharacter: LocalCharacter = characterResponse.toEntity()
                    val local: LocalCharacter? = localCharacterDataSource.getCharacterById(id)
                    if (local != null) {
                        localCharacterDataSource.updateCharacter(localCharacter.copy(isFavorite = local.isFavorite))
                    } else {
                        localCharacterDataSource.insert(localCharacter)
                    }
                    localCharacterDataSource
                        .getCharacterById(id)
                        ?.let {
                            it.toDomain().right()
                        } ?: RemoteError.Connectivity.left()
                }
            )

    override suspend fun updateCharacter(character: Character): Character {
        localCharacterDataSource.updateCharacter(character.fromDomain())
        return localCharacterDataSource.getCharacterById(character.id).toDomain()
    }

    private fun LocalCharacter?.toDomain() = Character(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.toDomain(),
        name = this?.name.orEmpty(),
        location = this?.location?.toDomain(),
        episode = this?.episode.orEmpty(),
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
        isFavorite = this?.isFavorite ?: false
    )

    private fun LocalOrigin?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun LocalLocation?.toDomain() = Location(
        name = this?.name,
        url = this?.url,
    )

    private fun Character?.fromDomain() = LocalCharacter(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.fromDomain(),
        name = this?.name.orEmpty(),
        location = this?.location?.fromDomain(),
        episode = this?.episode.orEmpty(),
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
        isFavorite = this?.isFavorite ?: false
    )

    private fun Origin?.fromDomain() = LocalOrigin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun Location?.fromDomain() = LocalLocation(
        name = this?.name,
        url = this?.url,
    )

}
