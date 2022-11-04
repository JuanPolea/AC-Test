package com.jfmr.ac.test.data.open.rickandmorty.episode.datasource

import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import kotlinx.coroutines.flow.Flow

interface EpisodesDataSource {
    fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<DomainEpisode>?>>
    suspend fun insertEpisodes(episodes: Episodes): List<Long>
}
