package com.jfmr.ac.test.data.repository.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.domain.model.episode.Episode

object EpisodeExtensions {

    fun List<EpisodeResponse>.toEntity() =
        filter {
            it.id != null
        }.map {
            it.toEntity()
        }

    fun EpisodeResponse.toEntity() =
        LocalEpisode(
            id = id ?: -1,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )

}
