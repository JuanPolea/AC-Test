package com.jfmr.ac.test.domain.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val origin: OriginEntity = OriginEntity(),
    val name: String = "",
    val location: LocationEntity = LocationEntity(),
    val episode: List<String> = emptyList(),
    val type: String = "",
    val url: String = "",
    val status: String = "",
    val isFavorite: Boolean = false,
)

data class OriginEntity(
    val name: String = "",
    val url: String = "",
)

data class LocationEntity(
    val name: String = "",
    val url: String = "",
)
