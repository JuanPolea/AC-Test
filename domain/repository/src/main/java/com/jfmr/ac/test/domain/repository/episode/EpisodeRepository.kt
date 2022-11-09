package com.jfmr.ac.test.domain.repository.episode

import arrow.core.Either
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.DomainError
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun episodes(episodesList: List<String>): Flow<Either<DomainError, List<Episode>>?>
    suspend fun insert(episodes: Episodes): List<Long>
}
