package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import javax.inject.Inject

class RemoteEpisodesDataSourceImpl @Inject constructor(
    private val apiService: RickAndMortyAPI,
) : RemoteEpisodesDataSource {

    override suspend fun retrieveEpisodes(episodesList: List<String>): Result<List<EpisodeResponse?>?> =
        apiService.episodes(
            episodesList.map {
                Uri.parse(it).lastPathSegment?.toInt() ?: 0
            }
        )


}
