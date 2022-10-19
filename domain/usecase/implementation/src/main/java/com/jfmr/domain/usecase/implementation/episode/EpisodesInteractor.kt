package com.jfmr.domain.usecase.implementation.episode

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.repository.open.episode.EpisodeRepository
import com.jfmr.ac.test.domain.usecase.open.episode.EpisodesUseCase
import javax.inject.Inject

class EpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : EpisodesUseCase {

    override fun episodes(): PagingSource<Int, DomainEpisode> {
        return episodeRepository.episodes()
    }

    override suspend fun episodes(episodesList: List<String>): List<DomainEpisode> {
        return episodeRepository.episodes(episodesList)
    }

}
