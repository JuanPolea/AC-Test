package com.jfmr.ac.test.data.remote.episode.datasource

import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse

interface RemoteEpisodesDataSource {
    suspend fun retrieveEpisodes(episodesList: List<String>): Result<List<EpisodeResponse?>?>
}
