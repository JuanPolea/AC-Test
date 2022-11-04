@file:Suppress("ReturnCount")
package com.jfmr.ac.test.data.cache.db.character

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.open.rickandmorty.character.model.Character
import com.jfmr.ac.test.data.open.rickandmorty.character.model.CharactersResponse
import com.jfmr.ac.test.data.open.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainCharacters
import com.jfmr.ac.test.domain.model.character.Info
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import com.jfmr.ac.test.domain.model.character.RemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator @Inject constructor(
    private val localDB: RickAndMortyDB,
    private val networkService: RickAndMortyApiService,
) : RemoteMediator<Int, DomainCharacter>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DomainCharacter>): MediatorResult {

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
            val domainCharacters: DomainCharacters = page.let { networkService.characters(it).toDomain() }
            localDB.withTransaction {
                val keys = domainCharacters.results?.filterNotNull()?.map {
                    RemoteKeys(it.id.toLong(), domainCharacters.info?.prev.orEmpty(), domainCharacters.info?.next.orEmpty())
                }
                keys?.map {
                    if (it.nextKey.isNotEmpty()) {
                        localDB.remoteKeysDao().insertAll(keys)
                    }
                }
            }
            domainCharacters
                .results
                ?.filterNotNull()
                ?.map { domainCharacter ->
                    localDB
                        .characterDao()
                        .getCharacterById(domainCharacter.id)
                        ?.let {
                            domainCharacter.copy(isFavorite = it.isFavorite)
                        } ?: domainCharacter
                }
                .apply {
                    if (this?.isNotEmpty() == true) {
                        localDB.characterDao().insertCharacters(this)
                    }
                }
            return MediatorResult.Success(endOfPaginationReached = domainCharacters.results?.isEmpty() == true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DomainCharacter>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                localDB.remoteKeysDao().remoteKeysRepoId(repo.id.toLong())
            }
    }

    private fun CharactersResponse.toDomain() = DomainCharacters(results = results.toDomain(), info = info?.toDomain())

    private fun List<Character?>?.toDomain() = this?.filterNotNull()?.map { it.toDomain() }

    private fun Character.toDomain() = id?.let {
        DomainCharacter(image = image,
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
            status = status)
    }

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Info.toDomain() =
        Info(next = next, pages = pages, prev = prev, count = count)

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Origin?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Location?.toDomain() = Location(
        name = this?.name,
        url = this?.url,
    )
}
