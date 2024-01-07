package com.jfmr.ac.test.data.cache.entities.character.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

object LocalCharacterExtensions {
    fun Flow<PagingData<LocalCharacter>>.toDomain() =
        transform { paging ->
            emit(paging.map { localCharacter -> localCharacter.toDomain() })

        }

    fun LocalCharacter?.toDomain() =
        this?.let {
            Character(
                id = it.id,
                image = it.image,
                gender = it.gender,
                species = it.species,
                created = it.created,
                origin = it.origin.toDomain(),
                name = it.name,
                location = it.location.toDomain(),
                episode = it.episode,
                type = it.type,
                url = it.url,
                status = it.status,
                isFavorite = it.isFavorite
            )
        } ?: Character(id = -1)


    private fun LocalOrigin?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun LocalLocation?.toDomain() =
        this?.let {
            Location(
                name = name,
                url = url,
            )
        } ?: Location()

    fun Character?.fromDomain() =
        this?.let {
            LocalCharacter(
                id = it.id,
                image = it.image,
                gender = it.gender,
                species = it.species,
                created = it.created,
                origin = it.origin.fromDomain(),
                name = it.name,
                location = it.location.fromDomain(),
                episode = it.episode,
                type = it.type,
                url = it.url,
                status = it.status,
                isFavorite = it.isFavorite,
            )
        } ?: LocalCharacter(id = -1)

    private fun Origin?.fromDomain(): LocalOrigin =
        this?.let {
            LocalOrigin(
                name = it.name,
                url = it.url,
            )
        } ?: LocalOrigin()

    private fun Location?.fromDomain() =
        this?.let {
            LocalLocation(
                name = it.name,
                url = it.url,
            )
        } ?: LocalLocation()
}
