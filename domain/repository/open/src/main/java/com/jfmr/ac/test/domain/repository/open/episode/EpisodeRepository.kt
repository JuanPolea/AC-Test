package com.jfmr.ac.test.domain.repository.open.episode

import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun episodes(episodesList: List<String>): Flow<List<DomainEpisode>>
}
