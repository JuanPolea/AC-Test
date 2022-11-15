package com.jfmr.ac.test.data.repository.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.data.repository.episode.mapper.EpisodeResponseExtensions.toEntity
import com.jfmr.ac.test.tests.TestUtils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EpisodeResponseExtensionsTest {

    private lateinit var episodesResponse: Array<EpisodeResponse>
    private lateinit var episodes: Array<LocalEpisode>

    @Before
    fun setUp() {
        episodesResponse = TestUtils.getObjectFromJson("episodes.json", Array<EpisodeResponse>::class.java) as Array<EpisodeResponse>
        episodes = TestUtils.getObjectFromJson("episodes.json", Array<LocalEpisode>::class.java) as Array<LocalEpisode>
    }

    @Test
    fun toEntity() {
        val expected = episodesResponse.toList().toEntity()
        assertEquals(expected, episodes.toList())
    }
}
