package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import arrow.core.right
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.api.rickandmorty.extensions.tryCall
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteEpisodesDataSourceImpl @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : RemoteEpisodesDataSource {

    override fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<Episode>>> =
        flow {
            tryCall {
                val episodeResponse: Response<List<Episode?>?> =
                    apiService
                        .episodes(episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 })
                emit(
                    episodeResponse
                        .body()
                        ?.filterNotNull()
                        ?.right() ?: emptyList<Episode>().right()
                )
            }
        }
}
