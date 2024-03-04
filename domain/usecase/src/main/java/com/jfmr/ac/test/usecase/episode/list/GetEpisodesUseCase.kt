package com.jfmr.ac.test.usecase.episode.list

import com.jfmr.ac.test.domain.model.episode.Episode
import kotlinx.coroutines.flow.Flow

interface GetEpisodesUseCase {
    suspend operator fun invoke(
        episodesList: List<String>,
    ): Flow<Result<List<Episode>>>
}
