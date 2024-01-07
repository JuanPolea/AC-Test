package com.jfmr.ac.test.data.repository.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.fromDomain
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.toDomain
import com.jfmr.ac.test.data.paging.RickAndMortyRemoteMediator
import com.jfmr.ac.test.data.paging.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.data.remote.di.DispatcherIO
import com.jfmr.ac.test.data.remote.extensions.tryCall
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CharacterRepositoryImpl
@Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource,
    @DispatcherIO private val coroutineDispatcher: CoroutineDispatcher,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun characters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 20),
            remoteMediator = RickAndMortyRemoteMediator(
                localCharacterDataSource,
                characterRemoteDataSource
            ),
            pagingSourceFactory = { localCharacterDataSource.getCharacters() },
        ).flow.map {
            it.map {
                it.toDomain()
            }
        }

    override suspend fun getCharacterById(id: Int) = flow {
        tryCall {
            characterRemoteDataSource
                .retrieveCharacterById(id)
        }.fold(
            { error ->
                emit(
                    localCharacterDataSource
                        .getCharacterById(id)
                        ?.toDomain()
                        ?.right()
                        ?: error.left()
                )
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
                                    localCharacterDataSource
                                        .updateCharacter(localCharacter.copy(isFavorite = localCharacter.isFavorite))
                                } ?: localCharacterDataSource.insert(it)
                        }
                }
                emit(
                    localCharacterDataSource
                        .getCharacterById(id)
                        ?.toDomain()
                        ?.right()
                        ?: RemoteError.Connectivity.left()
                )
            }
        )
    }

    override fun updateCharacter(character: Character) = flow {
        localCharacterDataSource.updateCharacter(character.fromDomain())
        emit(localCharacterDataSource.getCharacterById(character.id)?.toDomain() ?: character)
    }.flowOn(coroutineDispatcher)
}
