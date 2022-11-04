package com.jfmr.domain.usecase.implementation.episode

import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.open.episode.EpisodeRepository
import com.jfmr.ac.test.domain.usecase.open.episode.GetEpisodesUseCase
import javax.inject.Inject

class GetEpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : GetEpisodesUseCase {

    override suspend fun invoke(
        episodesList: List<String>,
        success: (List<DomainEpisode>?) -> Unit,
        error: (DomainError) -> Unit,
    ) {
        episodeRepository
            .episodes(episodesList)
            .collect { domainResult ->
                domainResult
                    .fold(
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
