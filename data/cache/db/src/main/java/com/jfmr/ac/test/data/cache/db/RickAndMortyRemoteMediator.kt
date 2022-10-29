package com.jfmr.ac.test.data.cache.db

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jfmr.ac.test.data.open.rickandmorty.character.model.Character
import com.jfmr.ac.test.data.open.rickandmorty.character.model.CharacterDetailResponse
import com.jfmr.ac.test.data.open.rickandmorty.character.model.CharactersResponse
import com.jfmr.ac.test.data.open.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.CharacterDetail
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

        // The network load method takes an optional `after=<user.id>` parameter. For every
        // page after the first, we pass the last user ID to let it continue from where it
        // left off. For REFRESH, pass `null` to load the first page.
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            // In this example, we never need to prepend, since REFRESH will always load the
            // first page in the list. Immediately return, reporting end of pagination.
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyForLastItem(state)
                // We must explicitly check if the last item is `null` when appending,
                // since passing `null` to networkService is only valid for initial load.
                // If lastItem is `null` it means no items were loaded after the initial
                // REFRESH and there are no more items to load.
                val nextKey: String = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                val uri = Uri.parse(nextKey)
                val nextPageQuery = uri.getQueryParameter(API_PAGE)
                nextPageQuery?.toInt() ?: 0
            }
        }

        // Suspending network load via Retrofit. This doesn't need to be wrapped in a
        // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine CallAdapter
        // dispatches on a worker thread.
        try {
            val domainCharacters: DomainCharacters = page.let { networkService.characters(it).toDomain() }

            localDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDB.remoteKeysDao().clearRemoteKeys()
                    localDB.characterDao().deleteDomainCharacter()
                }
                val keys = domainCharacters.results?.filterNotNull()?.map {
                    RemoteKeys(it.id.toLong(), domainCharacters.info?.prev.orEmpty(), domainCharacters.info?.next.orEmpty())
                }
                keys?.map {
                    if (it.nextKey.isNotEmpty()) {
                        localDB.remoteKeysDao().insertAll(keys)
                    }
                }
            }
            domainCharacters.results?.filterNotNull()?.let { localDB.characterDao().insertCharacters(it) }
            return MediatorResult.Success(endOfPaginationReached = domainCharacters.results?.isEmpty() == true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DomainCharacter>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                Log.e("GetRemoteKey", repo.id.toString())
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

    private fun CharacterDetailResponse?.toDomain() = CharacterDetail(image = this?.image.orEmpty(),
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
        status = this?.status.orEmpty())

}
