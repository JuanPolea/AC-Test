package com.jfmr.ac.test.domain.usecase.open.episode

import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.error.DomainError

interface GetEpisodesUseCase {
    suspend operator fun invoke(
        episodesList: List<String>,
        success: (List<DomainEpisode>?) -> Unit,
        error: (DomainError) -> Unit,
    )
}
