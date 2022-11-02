package com.jfmr.ac.test.data.open.rickandmorty.character.model

import com.google.gson.annotations.SerializedName

data class Origin(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)