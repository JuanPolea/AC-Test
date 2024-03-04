package com.jfmr.ac.test.usecase.episode.list

import com.jfmr.ac.test.data.repository.episode.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : GetEpisodesUseCase {

    override suspend operator fun invoke(episodesList: List<String>): Flow<Result<List<Episode>>> =
        episodeRepository.episodes(episodesList)
}
