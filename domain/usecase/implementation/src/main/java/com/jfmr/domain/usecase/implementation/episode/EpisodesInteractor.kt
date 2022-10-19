package com.jfmr.domain.usecase.implementation.episode

import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.repository.open.episode.EpisodeRepository
import com.jfmr.ac.test.domain.usecase.open.episode.EpisodesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : EpisodesUseCase {

    override fun episodes(episodesList: List<String>): Flow<List<DomainEpisode>> =
        episodeRepository.episodes(episodesList)

}
