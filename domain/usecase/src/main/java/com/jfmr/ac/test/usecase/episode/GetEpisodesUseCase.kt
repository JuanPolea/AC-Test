package com.jfmr.ac.test.usecase.episode

import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError

interface GetEpisodesUseCase {
    suspend operator fun invoke(
        episodesList: List<String>,
        success: (List<Episode>?) -> Unit,
        error: (DomainError) -> Unit,
    )
}
