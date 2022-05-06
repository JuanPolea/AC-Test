package com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities

import com.google.gson.annotations.SerializedName

data class Origin(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)
