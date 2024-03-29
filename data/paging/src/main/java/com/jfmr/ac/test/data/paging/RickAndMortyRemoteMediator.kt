@file:Suppress("ReturnCount")

package com.jfmr.ac.test.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys
import com.jfmr.ac.test.data.paging.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator @Inject constructor(
    private val localCharacterDataSource: LocalCharacterDataSource,
    private val characterRemoteDataSource: CharacterRemoteDataSource,
) : RemoteMediator<Int, LocalCharacter>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalCharacter>
    ): MediatorResult {
        val page: Int? = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyForLastItem(state)
                val nextKey: String = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                val uri = Uri.parse(nextKey)
                val nextPageQuery = uri.getQueryParameter(API_PAGE)
                nextPageQuery?.toInt()
            }
        }

        return try {
            val characters: CharactersResponse? = page?.let {
                characterRemoteDataSource.retrieveCharacters(it).getOrNull()
            }
            localCharacterDataSource
                .geLocalDB()
                .withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localCharacterDataSource
                            .geLocalDB().remoteKeysDao().clearRemoteKeys()
                        localCharacterDataSource
                            .geLocalDB().characterDao().clearCharacters()
                    }
                    characters?.let {
                        characters
                            .results
                            ?.filterNotNull()
                            ?.mapNotNull { characterResponse ->
                                characterResponse
                                    .id
                                    ?.toLong()
                                    ?.let { id ->
                                        RemoteKeys(
                                            id,
                                            characters.info?.prev.orEmpty(),
                                            characters.info?.next.orEmpty()
                                        )
                                    }
                            }?.also { remoteKeys ->
                                localCharacterDataSource.insertRemoteKeys(remoteKeys)
                            }
                        characters
                            .results
                            ?.filter {
                                it?.id != null
                            }
                            ?.filterNotNull()
                            ?.map { characterResponse ->
                                val character = characterResponse.toEntity()
                                localCharacterDataSource
                                    .getCharacterById(character.id)
                                    ?.let {
                                        characterResponse.copy(isFavorite = it.isFavorite)
                                    } ?: characterResponse
                            }
                            ?.let { list ->
                                if (list.isNotEmpty()) {
                                    localCharacterDataSource
                                        .insertCharacters(list.map { it.toEntity() })
                                }
                            }
                    }
                }
            MediatorResult.Success(endOfPaginationReached = characters?.results?.isEmpty() == true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalCharacter>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                localCharacterDataSource.geLocalDB().withTransaction {
                    localCharacterDataSource.remoteKeysId(repo.id.toLong())
                }

            }
    }
}
