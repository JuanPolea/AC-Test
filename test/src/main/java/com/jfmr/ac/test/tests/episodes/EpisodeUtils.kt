package com.jfmr.ac.test.tests.episodes

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.TestUtils

private const val EPISODE = "episode.json"
private const val EPISODES = "episodes.json"

object EpisodeUtils {

    val episodeResponses = TestUtils.getObjectFromJson(EPISODE, EpisodeResponse::class.java) as EpisodeResponse
    val episode = TestUtils.getObjectFromJson(EPISODE, Episode::class.java) as Episode
    val episodesResponse = TestUtils.getObjectFromJson(EPISODES, Array<EpisodeResponse>::class.java) as Array<EpisodeResponse>


    val expectedEpisodes = TestUtils.getObjectFromJson(EPISODES, Array<Episode>::class.java) as Array<Episode>
    val expectedStringEpisode = TestUtils.getObjectAsString(EPISODE)
    val expectedStringEpisodes = TestUtils.getObjectAsString(EPISODES)


}
