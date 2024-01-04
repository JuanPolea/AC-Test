package com.jfmr.ac.test.presentation.ui.character.list.model

import android.os.Parcelable
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterUI(
    val id: Int = -1,
    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val origin: OriginUI = OriginUI(),
    val name: String = "",
    val location: LocationUI = LocationUI(),
    val episode: List<String> = emptyList(),
    val type: String = "",
    val url: String = "",
    val status: String = "",
    val isFavorite: Boolean = false,
) : Parcelable

@Parcelize
data class OriginUI(
    val name: String = "",
    val url: String = "",
) : Parcelable

@Parcelize
data class LocationUI(
    val name: String = "",
    val url: String = "",
) : Parcelable

sealed class CharacterListEvent {
    data class AddToFavorite(val character: CharacterUI) : CharacterListEvent()
}

internal fun Character.toUI() =
    CharacterUI(
        id = id,
        image = image,
        gender = gender,
        species = species,
        created = created,
        origin = origin.toUI(),
        name = name,
        location = location.toUI(),
        episode = episode,
        type = type,
        url = url,
        status = status,
        isFavorite = isFavorite

    )

internal fun Origin.toUI() =
    OriginUI(
        name = name,
        url = url
    )

internal fun Location.toUI() =
    LocationUI(
        name = name,
        url = url
    )

internal fun CharacterUI.toDomain() =
    Character(
        id = id,
        image = image,
        gender = gender,
        species = species,
        created = created,
        origin = origin.toDomain(),
        name = name,
        location = location.toDomain(),
        episode = episode,
        type = type,
        url = url,
        status = status,
        isFavorite = isFavorite

    )

internal fun OriginUI.toDomain() =
    Origin(
        name = name,
        url = url
    )

internal fun LocationUI.toDomain() =
    Location(
        name = name,
        url = url
    )
