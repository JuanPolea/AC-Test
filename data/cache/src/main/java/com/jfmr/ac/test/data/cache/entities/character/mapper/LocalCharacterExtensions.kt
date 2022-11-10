package com.jfmr.ac.test.data.cache.entities.character.mapper

import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin

object LocalCharacterExtensions {

     fun LocalCharacter?.toDomain() = Character(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.toDomain(),
        name = this?.name.orEmpty(),
        location = this?.location?.toDomain(),
        episode = this?.episode.orEmpty(),
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
        isFavorite = this?.isFavorite ?: false
    )

     fun LocalOrigin?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

     fun LocalLocation?.toDomain() = Location(
        name = this?.name,
        url = this?.url,
    )
     fun Character?.fromDomain() = LocalCharacter(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.fromDomain(),
        name = this?.name.orEmpty(),
        location = this?.location?.fromDomain(),
        episode = this?.episode.orEmpty(),
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
        isFavorite = this?.isFavorite ?: false
    )

     fun Origin?.fromDomain() = LocalOrigin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

     fun Location?.fromDomain() = LocalLocation(
        name = this?.name,
        url = this?.url,
    )
}
