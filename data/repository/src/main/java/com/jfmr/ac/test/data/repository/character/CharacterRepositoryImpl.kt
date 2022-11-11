package com.jfmr.ac.test.data.repository.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.paging.RickAndMortyRemoteMediator
import com.jfmr.ac.test.data.paging.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.data.remote.character.extensions.tryCall
import com.jfmr.ac.test.data.repository.character.mapper.LocalCharacterExtensions.fromDomain
import com.jfmr.ac.test.data.repository.character.mapper.LocalCharacterExtensions.toDomain
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class CharacterRepositoryImpl
@Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun characters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RickAndMortyRemoteMediator(localCharacterDataSource, characterRemoteDataSource),
            pagingSourceFactory = { localCharacterDataSource.getCharacters() }
        ).flow
            .mapLatest { paging ->
                paging.map { localCharacter ->
                    localCharacter.toDomain()
                }
            }
    }

    override suspend fun getCharacterById(id: Int): DomainResult<Character> =
        tryCall {
            characterRemoteDataSource
                .retrieveCharacterById(id)
        }.fold(
            { error ->
                localCharacterDataSource
                    .getCharacterById(id)
                    ?.toDomain()
                    ?.right()
                    ?: error.left()
            },
            { response ->
                if (response.isSuccessful) {
                    response
                        .body()
                        .toEntity()
                        .also {
                            localCharacterDataSource
                                .getCharacterById(id)
                                ?.let { localCharacter ->
                                    localCharacterDataSource.updateCharacter(localCharacter.copy(isFavorite = localCharacter.isFavorite))
                                } ?: localCharacterDataSource.insert(it)
                        }
                }
                localCharacterDataSource
                    .getCharacterById(id)
                    ?.toDomain()
                    ?.right()
                    ?: RemoteError.Connectivity.left()
            }
        )

    override suspend fun updateCharacter(character: Character): Character {
        localCharacterDataSource.updateCharacter(character.fromDomain())
        return localCharacterDataSource.getCharacterById(character.id)?.toDomain() ?: character
    }
}
