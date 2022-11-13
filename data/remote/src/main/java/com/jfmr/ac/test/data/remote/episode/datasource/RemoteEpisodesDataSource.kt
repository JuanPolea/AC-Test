package com.jfmr.ac.test.data.remote.episode.datasource

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import retrofit2.Response

interface RemoteEpisodesDataSource {
    suspend fun retrieveEpisodes(episodesList: List<String>): Response<List<EpisodeResponse?>?>
}
