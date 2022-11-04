package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import android.util.Log
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.RemoteError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteEpisodesDataSource @Inject constructor(
    private val apiService: RickAndMortyApiService,
    private val localDB: RickAndMortyDB,
) : EpisodesDataSource {

    override fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<DomainEpisode>>> =
        flow {
            try {
                val episodeResponse =
                    apiService
                        .episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 })
                if (episodeResponse.isSuccessful) {
                    episodeResponse
                        .body()
                        ?.let { episodes ->
                            episodes
                                .filterNotNull()
                                ?.let { list ->
                                    val tema = localDB.episodesDao().insertEpisodes(list)
                                    Log.e("JAJFSDAS", tema.toString())
                                }
                        }
                }
                val result: List<DomainEpisode>? = localDB.episodesDao().episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 })
                emit(result?.right() ?: RemoteError.Connectivity.left())
            } catch (e: Exception) {
                val error = when (e) {
                    is IOException -> RemoteError.Connectivity
                    is HttpException -> RemoteError.Server(e.code())
                    else -> RemoteError.Unknown(e.message.orEmpty())
                }
                emit(localDB.episodesDao().episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 })?.right() ?: error.left())
            }
        }


    override suspend fun insertEpisodes(episodes: Episodes): List<Long> =
        episodes.results?.filterNotNull()?.let { localDB.episodesDao().insertEpisodes(it) } ?: emptyList()
}
