package com.jfmr.ac.test.data.remote

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jfmr.ac.test.data.open.mapper.tryCall
import com.jfmr.ac.test.data.open.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.open.rickandmorty.entities.Character
import com.jfmr.ac.test.data.open.rickandmorty.entities.CharacterDetailResponse
import com.jfmr.ac.test.data.open.rickandmorty.entities.CharactersResponse
import com.jfmr.ac.test.data.open.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.DomainCharacter
import com.jfmr.ac.test.domain.model.DomainCharacters
import javax.inject.Inject


class RetrieveRemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService,
) : RetrieveCharactersDataSource {

    override fun characters(): PagingSource<Int, DomainCharacter> =
        object : PagingSource<Int, DomainCharacter>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainCharacter> {
                val pageNumber = params.key ?: 1
                return try {
                    val response: DomainCharacters = remoteService.characters(pageNumber).toDomain()
                    val list: List<DomainCharacter> = response.results?.filterNotNull() ?: emptyList()
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

    override suspend fun retrieveCharacterDetail(characterId: Int) = tryCall {
        remoteService.retrieveCharacterById(characterId).body().toDomain()
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

    private fun com.jfmr.ac.test.data.open.rickandmorty.entities.Info.toDomain() =
        com.jfmr.ac.test.domain.model.Info(next = next, pages = pages, prev = prev, count = count)

    private fun com.jfmr.ac.test.data.open.rickandmorty.entities.Origin?.toDomain() = com.jfmr.ac.test.domain.model.Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun com.jfmr.ac.test.data.open.rickandmorty.entities.Location?.toDomain() = com.jfmr.ac.test.domain.model.Location(
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
