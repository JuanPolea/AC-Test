package com.jfmr.ac.test.data.repository.episode.mapper

import com.jfmr.ac.test.data.repository.episode.mapper.EpisodeResponseExtensions.toEntity
import com.jfmr.ac.test.data.repository.utils.LocalUtils
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.episodesResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EpisodeResponseExtensionsTest {

    @Before
    fun setUp() {
    }

    @Test
    fun toEntity() {
        val expected = episodesResponse.toList().toEntity()
        assertEquals(expected, LocalUtils.expectedLocalEpisodes.toList())
    }
}
