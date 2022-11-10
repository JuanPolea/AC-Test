package com.jfmr.ac.test.data.remote.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.domain.model.episode.Episode

object EpisodeExtensions {

    fun EpisodeResponse.toDomain() =
        Episode(
            id = id ?: -1,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )
}
