package com.jfmr.ac.test.data.api.rickandmorty.character.entity.mapper

import com.jfmr.ac.test.data.cache.entities.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.LocalLocation
import com.jfmr.ac.test.data.cache.entities.LocalOrigin
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.LocationResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.OriginResponse
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin

object CharacterExtensions {

    fun CharacterResponse?.toEntity() = LocalCharacter(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.toEntity(),
        name = this?.name.orEmpty(),
        location = this?.location?.toEntity(),
        episode = this?.episode.orEmpty(),
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
    )

    private fun OriginResponse?.toEntity() = LocalOrigin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun LocationResponse?.toEntity() = LocalLocation(
        name = this?.name,
        url = this?.url,
    )


    fun CharacterResponse.toDomain() = com.jfmr.ac.test.domain.model.character.Character(
        id = id ?: -1,
        image = image.orEmpty(),
        gender = gender.orEmpty(),
        species = species.orEmpty(),
        created = created.orEmpty(),
        origin = origin?.toDomain(),
        name = name.orEmpty(),
        location = location?.toDomain(),
        episode = episode.orEmpty(),
        type = type.orEmpty(),
        url = url.orEmpty(),
        status = status.orEmpty(),
    )

    private fun OriginResponse?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun LocationResponse?.toDomain() = Location(
        name = this?.name,
        url = this?.url,
    )


}
