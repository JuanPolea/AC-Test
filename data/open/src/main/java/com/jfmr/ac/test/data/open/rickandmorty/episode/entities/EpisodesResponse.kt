package com.jfmr.ac.test.data.open.rickandmorty.episode.entities


import com.google.gson.annotations.SerializedName

data class EpisodesResponse(

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,

    @field:SerializedName("info")
    val info: Info? = null,
)

data class ResultsItem(

    @field:SerializedName("air_date")
    val airDate: String? = null,

    @field:SerializedName("characters")
    val characters: List<String?>? = null,

    @field:SerializedName("created")
    val created: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("episode")
    val episode: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,
)

data class Info(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("pages")
    val pages: Int? = null,

    @field:SerializedName("prev")
    val prev: Any? = null,

    @field:SerializedName("count")
    val count: Int? = null,
)
