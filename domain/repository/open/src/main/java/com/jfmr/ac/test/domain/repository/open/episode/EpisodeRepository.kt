package com.jfmr.ac.test.domain.repository.open.episode

import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun episodes(episodesList: List<String>): Flow<DomainResult<List<DomainEpisode>?>>
    suspend fun insert(episodes: Episodes): List<Long>
}
