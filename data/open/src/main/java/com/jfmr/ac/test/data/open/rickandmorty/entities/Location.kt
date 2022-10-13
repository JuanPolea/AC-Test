package com.jfmr.ac.test.data.open.rickandmorty.entities

import com.google.gson.annotations.SerializedName

data class Location(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)
