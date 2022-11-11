package com.jfmr.ac.test.usecase.episode.implementation

import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import com.jfmr.ac.test.usecase.episode.GetEpisodesUseCase
import javax.inject.Inject

class GetEpisodesInteractor @Inject constructor(
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : GetEpisodesUseCase {

    override suspend fun invoke(
        episodesList: List<String>,
        success: (List<Episode>?) -> Unit,
        error: (DomainError) -> Unit,
    ) {
        episodeRepository
            .episodes(episodesList)
            .collect { domainResult ->
                domainResult
                    ?.let {
                        it.fold(
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

}
