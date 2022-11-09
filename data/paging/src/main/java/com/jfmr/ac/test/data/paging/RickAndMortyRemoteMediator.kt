@file:Suppress("ReturnCount")

package com.jfmr.ac.test.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.mapper.CharacterExtensions.toDomain
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.api.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.RemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator @Inject constructor(
    private val localDB: RickAndMortyDB,
    private val networkService: RickAndMortyApiService,
) : RemoteMediator<Int, LocalCharacter>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, LocalCharacter>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyForLastItem(state)
                val nextKey: String = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                val uri = Uri.parse(nextKey)
                val nextPageQuery = uri.getQueryParameter(API_PAGE)
                nextPageQuery?.toInt() ?: 0
            }
        }

        try {
            val characters: CharactersResponse = page.let { networkService.characters(it) }
            localDB.withTransaction {
                characters
                    .results
                    ?.filterNotNull()
                    ?.mapNotNull { characterResponse ->
                        characterResponse
                            .id
                            ?.toLong()
                            ?.let { id ->
                                RemoteKeys(id, characters.info?.prev.orEmpty(), characters.info?.next.orEmpty())
                            }
                    }?.also { remoteKeys ->
                        localDB.remoteKeysDao().insertAll(remoteKeys)
                    }
                characters
                    .results
                    ?.filter {
                        it?.id != null
                    }
                    ?.filterNotNull()
                    ?.map { characterResponse ->
                        val character = characterResponse.toDomain()
                        localDB
                            .characterDao()
                            .getCharacterById(character.id)
                            ?.let {
                                characterResponse.copy(isFavorite = it.isFavorite)
                            } ?: characterResponse
                    }
                    ?.let { characters ->
                        if (characters.isNotEmpty()) {
                            localDB
                                .characterDao()
                                .insertCharacters(characters.map { it.toEntity() })
                        }
                    }
            }
            return MediatorResult.Success(endOfPaginationReached = characters.results?.isEmpty() == true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalCharacter>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                localDB.remoteKeysDao().remoteKeysRepoId(repo.id.toLong())
            }
    }
}
