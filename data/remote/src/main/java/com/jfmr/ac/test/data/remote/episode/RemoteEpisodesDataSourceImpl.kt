package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteEpisodesDataSourceImpl @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : RemoteEpisodesDataSource {

    override suspend fun retrieveEpisodes(episodesList: List<String>): Response<List<EpisodeResponse?>?> =
        apiService.episodes(
            episodesList.map {
                Uri.parse(it).lastPathSegment?.toInt() ?: 0
            }
        )


}
