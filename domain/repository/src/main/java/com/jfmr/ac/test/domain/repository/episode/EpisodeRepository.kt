package com.jfmr.ac.test.domain.repository.episode

import com.jfmr.ac.test.domain.model.DomainResult
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun episodes(episodesList: List<String>): Flow<DomainResult<List<Episode>>>
    suspend fun insert(episodes: Episodes): List<Long>
}
