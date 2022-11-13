package com.jfmr.ac.test.data.remote.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.remote.episode.mapper.EpisodeExtensions.toDomain
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.TestUtils
import org.junit.Before
import org.junit.Test

class EpisodeExtensionsTest {

    private lateinit var episodeResponses: EpisodeResponse
    private lateinit var episode: Episode

    @Before
    fun setUp() {
        episodeResponses = TestUtils.getObjectFromJson("episode.json", EpisodeResponse::class.java) as EpisodeResponse
        episode = TestUtils.getObjectFromJson("episode.json", Episode::class.java) as Episode
    }


    @Test
    fun toDomain() {
        val actual: Episode? = episodeResponses.toDomain()
        kotlin.test.assertEquals(episode, actual)
    }
}
