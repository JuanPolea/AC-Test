package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.EpisodesResponse
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.ResultsItem
import com.jfmr.ac.test.data.open.rickandmorty.network.API_PAGE
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import javax.inject.Inject

class RemoteEpisodesDataSource @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : EpisodesDataSource {

    override fun retrieveEpisodes(): PagingSource<Int, DomainEpisode> {
        return object : PagingSource<Int, DomainEpisode>() {
            override fun getRefreshKey(state: PagingState<Int, DomainEpisode>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainEpisode> {
                val pageNumber = params.key ?: 0
                return try {
                    val episodes = apiService.episodes(pageNumber).toDomain()
                    val domainEpisodes: List<DomainEpisode> = episodes.results?.filterNotNull() ?: emptyList()

                    var nextPageNumber: Int? = null
                    if (episodes.domainInfo?.next != null) {
                        val uri = Uri.parse(episodes.domainInfo!!.next)
                        val nextPageQuery = uri.getQueryParameter(API_PAGE)
                        nextPageNumber = nextPageQuery?.toInt()
                    }
                    var prevPageNumber: Int? = null
                    if (episodes.domainInfo?.prev != null) {
                        val uri = Uri.parse(episodes.domainInfo!!.prev)
                        val nextPageQuery = uri.getQueryParameter(API_PAGE)
                        prevPageNumber = nextPageQuery?.toInt()
                    }
                    LoadResult.Page(
                        data = domainEpisodes,
                        prevKey = prevPageNumber,
                        nextKey = nextPageNumber
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

        }
    }

    override suspend fun retrieveEpisodes(episodesList: List<String>): List<DomainEpisode> {

        return apiService.episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 }).body()?.map { it.toDomain() } ?: emptyList()
    }

    private fun EpisodesResponse?.toDomain(): Episodes =
        Episodes(
            results = this?.results?.filterNotNull()?.map { it.toDomain() }
        )

    private fun ResultsItem.toDomain() =
        DomainEpisode(
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            id = id,
            url = url
        )

}
