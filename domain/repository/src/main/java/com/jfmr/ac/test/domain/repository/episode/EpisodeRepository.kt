package com.jfmr.ac.test.domain.repository.episode

import arrow.core.Either
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.DomainError

interface EpisodeRepository {

    suspend fun episodes(episodesList: List<String>): Either<DomainError, List<Episode>>
    suspend fun insert(episodes: Episodes): List<Long>
}
