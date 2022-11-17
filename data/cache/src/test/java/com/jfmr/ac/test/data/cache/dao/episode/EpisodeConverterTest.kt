package com.jfmr.ac.test.data.cache.dao.episode

import com.jfmr.ac.test.tests.episodes.EpisodeUtils.expectedStringEpisode
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.expectedStringEpisodes
import com.jfmr.ac.test.utils.LocalUtils.expectedLocalEpisodes
import org.junit.Assert.assertEquals
import org.junit.Test

class EpisodeConverterTest {


    private val episodeConverter = EpisodeConverter()

    @Test
    fun jsonToLocalEpisode() {
        assertEquals(expectedLocalEpisodes.first(), episodeConverter.jsonToLocalEpisode(expectedStringEpisode))
    }

    @Test
    fun localEpisodeToJson() {
        assertEquals(expectedStringEpisode.replace(" ", ""), episodeConverter.localEpisodeToJson(expectedLocalEpisodes.first()).replace(" ", ""))
    }


    @Test
    fun localEpisodesToJson() {
        assertEquals(expectedStringEpisodes.replace(" ", ""), episodeConverter.localEpisodesToJson(expectedLocalEpisodes.toList()).replace(" ", ""))
    }

    @Test
    fun jsonToLocalEpisodes() {
        episodeConverter
            .jsonToLocalEpisodes(expectedStringEpisodes)
            .forEachIndexed { index, localEpisode ->
                kotlin.test.assertEquals(expectedLocalEpisodes[index], localEpisode)
            }
    }

}
