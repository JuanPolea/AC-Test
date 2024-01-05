package com.jfmr.ac.test.data.api.rickandmorty.character.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class OriginResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)
