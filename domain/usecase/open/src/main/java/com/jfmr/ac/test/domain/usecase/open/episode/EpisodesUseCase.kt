package com.jfmr.ac.test.domain.usecase.open.episode

import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import kotlinx.coroutines.flow.Flow

interface EpisodesUseCase {
    fun episodes(episodesList: List<String>): Flow<List<DomainEpisode>>
}
