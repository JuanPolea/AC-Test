package com.jfmr.ac.test.data.remote.character

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.db.RickAndMortyRemoteMediator
import com.jfmr.ac.test.data.open.mapper.tryCall
import com.jfmr.ac.test.data.open.rickandmorty.character.datasource.CharactersDataSource
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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    private val rickAndMortyDB: RickAndMortyDB,
) : CharactersDataSource {

    override fun characters(): PagingSource<Int, DomainCharacter> =
        object : PagingSource<Int, DomainCharacter>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainCharacter> {
                val pageNumber = params.key ?: 1
                return try {
                    val response: DomainCharacters = remoteService.characters(pageNumber).toDomain()
                    val list: List<DomainCharacter> = response.results?.filterNotNull() ?: emptyList()
                    if (list.isNotEmpty()) {
                        list.map {
                            Log.e("Characters", characterDao.insertDomainCharacter(it).toString())
                        }
                    }
                    var nextPageNumber: Int? = null
                    if (response.info?.next != null) {
                        val uri = Uri.parse(response.info!!.next)
                        val nextPageQuery = uri.getQueryParameter(API_PAGE)
                        nextPageNumber = nextPageQuery?.toInt()
                    }
                    var prevPageNumber: Int? = null
                    if (response.info?.prev != null) {
                        val uri = Uri.parse(response.info!!.prev)
                        val nextPageQuery = uri.getQueryParameter(API_PAGE)
                        prevPageNumber = nextPageQuery?.toInt()
                    }
                    LoadResult.Page(
                        data = list,
                        prevKey = prevPageNumber,
                        nextKey = nextPageNumber
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, DomainCharacter>): Int? =
                state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchCharacters(): Flow<PagingData<DomainCharacter>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 25,
            ),
            remoteMediator = RickAndMortyRemoteMediator(rickAndMortyDB, remoteService),
            pagingSourceFactory = { rickAndMortyDB.characterDao().getPaginatedDomainCharacter() }
        ).flow
    }

    override suspend fun retrieveCharacterDetail(characterId: Int) = tryCall {
        remoteService.retrieveCharacterById(characterId).body().toDomain()
    }


    private fun CharactersResponse.toDomain(): DomainCharacters = DomainCharacters(results = results.toDomain(), info = info?.toDomain())

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

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Info.toDomain(): Info =
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
