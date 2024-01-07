package com.jfmr.ac.test.usecase.episode.list

import com.jfmr.ac.test.data.repository.episode.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import javax.inject.Inject

class GetEpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : GetEpisodesUseCase {

    override suspend operator fun invoke(
        episodesList: List<String>,
        success: (List<Episode>?) -> Unit,
        error: (DomainError) -> Unit,
    ) {
        episodeRepository
            .episodes(episodesList)
            .collect { domainResult ->
                domainResult.fold(
                    {
                        error(it)
                    },
                    {
                        success(it)
                    }
                )
            }

    }
}
