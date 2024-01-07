package com.jfmr.ac.test.data.repository.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode

object EpisodeResponseExtensions {

    fun List<EpisodeResponse>.toEntity() =
        filter {
            it.id != null
        }.mapNotNull {
            it.toEntity()
        }

    private fun EpisodeResponse.toEntity(): LocalEpisode? =
        id?.let { id ->
            LocalEpisode(
                id = id,
                airDate = airDate.orEmpty(),
                characters = characters?.filterNotNull() ?: emptyList(),
                created = created.orEmpty(),
                name = name.orEmpty(),
                episode = episode.orEmpty(),
                url = url.orEmpty()
            )
        }
}
