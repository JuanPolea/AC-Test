package com.jfmr.ac.test.data.remote.episode.mapper

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.remote.episode.mapper.EpisodeExtensions.toDomain
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.episodes.EpisodeUtils
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNull

class EpisodeExtensionsTest {

    private lateinit var episodeResponses: EpisodeResponse
    private lateinit var episode: Episode

    @Before
    fun setUp() {
        episodeResponses = EpisodeUtils.episodeResponses
        episode = EpisodeUtils.episode
    }


    @Test
    fun toDomain_EpisodeResponse_Episode() {
        val actual: Episode? = episodeResponses.toDomain()
        kotlin.test.assertEquals(episode, actual)

    }

    @Test
    fun toDomain_NullEpisodeResponse_Null() {
        val actual: Episode? = episodeResponses.toDomain()
        kotlin.test.assertEquals(episode, actual)

        val nullEpisode: Episode? = null.toDomain()

        assertNull(nullEpisode)

        val episodeIdNull = episodeResponses.copy(id = null).toDomain()
        assertNull(episodeIdNull)

    }

    @Test
    fun toDomain_EpisodeResponseIdNull_Null() {
        val episodeIdNull = episodeResponses.copy(id = null).toDomain()
        assertNull(episodeIdNull)

    }
}
