package com.jfmr.ac.test.data.remote.episode.datasource

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

interface RemoteEpisodesDataSource {
    fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<EpisodeResponse>>>
}
