package com.jfmr.ac.test.domain.model.character

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


data class DomainCharacters(
    val results: List<DomainCharacter?>? = null,
    val info: Info? = null,
)


data class Info(
    val next: String? = null,
    val pages: Int? = null,
    val prev: String? = null,
    val count: Int? = null,
)

@Parcelize
@Serializable
@Entity(tableName = "characters")
data class DomainCharacter(
    @PrimaryKey
    val id: Int,
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val created: String? = null,
    val origin: Origin? = null,
    val name: String? = null,
    val location: Location? = null,
    val episode: List<String?>? = null,
    val type: String? = null,
    val url: String? = null,
    val status: String? = null,
) : Parcelable

@Parcelize
@Serializable
data class Origin(
    val name: String? = null,
    val url: String? = null,
) : Parcelable

@Parcelize
@Serializable
data class Location(
    val name: String? = "",
    val url: String? = "",
) : Parcelable
