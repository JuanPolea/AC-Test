package com.jfmr.ac.test.data.open.rickandmorty.episode.datasource

import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import kotlinx.coroutines.flow.Flow

interface EpisodesDataSource {
    fun retrieveEpisodes(episodesList: List<String>): Flow<List<DomainEpisode>>
}
