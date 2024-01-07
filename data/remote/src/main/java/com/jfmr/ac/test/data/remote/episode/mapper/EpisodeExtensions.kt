package com.jfmr.ac.test.data.remote.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import com.jfmr.ac.test.domain.model.episode.Episode

object EpisodeExtensions {

    fun EpisodeResponse?.toDomain(): Episode? =
        this?.let {
            id?.let {
                Episode(
                    id = it,
                    airDate = airDate.orEmpty(),
                    characters = characters?.filterNotNull() ?: emptyList(),
                    created = created.orEmpty(),
                    name = name.orEmpty(),
                    episode = episode.orEmpty(),
                    url = url.orEmpty()
                )
            }
        }

}
