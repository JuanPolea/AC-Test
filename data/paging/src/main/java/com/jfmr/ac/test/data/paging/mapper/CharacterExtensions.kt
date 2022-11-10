package com.jfmr.ac.test.data.paging.mapper

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.LocationResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.OriginResponse
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin

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
}
