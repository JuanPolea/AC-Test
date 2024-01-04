package com.jfmr.ac.test.data.api.rickandmorty.character.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CharactersResponse(
    @field:SerializedName("results")
    val results: List<CharacterResponse?>? = null,
    @field:SerializedName("info")
    val info: CharacterInfo? = null,
)


@Serializable
data class CharacterInfo(
    @field:SerializedName("next")
    val next: String? = null,
    @field:SerializedName("pages")
    val pages: Int? = null,
    @field:SerializedName("prev")
    val prev: String? = null,
    @field:SerializedName("count")
    val count: Int? = null,
)


@Serializable
data class CharacterResponse(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("image")
    val image: String? = null,
    @field:SerializedName("gender")
    val gender: String? = null,
    @field:SerializedName("species")
    val species: String? = null,
    @field:SerializedName("created")
    val created: String? = null,
    @field:SerializedName("origin")
    val origin: OriginResponse? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("location")
    val location: LocationResponse? = null,
    @field:SerializedName("episode")
    val episode: List<String?>? = null,
    @field:SerializedName("type")
    val type: String? = null,
    @field:SerializedName("url")
    val url: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @Transient
    @field:SerializedName("isFavorite")
    val isFavorite: Boolean = false,
)
