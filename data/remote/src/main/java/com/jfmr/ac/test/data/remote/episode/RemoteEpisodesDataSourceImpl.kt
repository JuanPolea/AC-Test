package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import arrow.core.right
import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.remote.character.extensions.tryCall
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteEpisodesDataSourceImpl @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : RemoteEpisodesDataSource {

    override fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<EpisodeResponse>>> =
        flow {
            tryCall {
                val episodeResponse: Response<List<EpisodeResponse?>?> =
                    apiService.episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 })
                emit(
                    episodeResponse
                        .body()
                        ?.filterNotNull()
                        ?.right()
                        ?: emptyList<EpisodeResponse>().right()
                )
            }
        }


}
