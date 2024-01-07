package com.jfmr.ac.test.data.paging.mapper

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.LocationResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.OriginResponse
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin

object CharacterExtensions {

    fun CharacterResponse?.toEntity() =
        this?.let {
            LocalCharacter(
                id = it.id ?: -1,
                image = image.orEmpty(),
                gender = gender.orEmpty(),
                species = species.orEmpty(),
                created = created.orEmpty(),
                origin = origin.toEntity(),
                name = name.orEmpty(),
                location = location.toEntity(),
                episode = episode?.filterNotNull().orEmpty(),
                type = type.orEmpty(),
                url = url.orEmpty(),
                status = status.orEmpty(),
                isFavorite = isFavorite
            )
        } ?: LocalCharacter(id = -1)

    private fun OriginResponse?.toEntity() =
        this?.let {
            LocalOrigin(
                name = name.orEmpty(),
                url = url.orEmpty(),
            )
        } ?: LocalOrigin()


    private fun LocationResponse?.toEntity() =
        this?.let {
            LocalLocation(
                name = name.orEmpty(),
                url = url.orEmpty(),
            )
        } ?: LocalLocation()
}
