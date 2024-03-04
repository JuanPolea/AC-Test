package com.jfmr.ac.test.data.remote.episode

import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_SERVER_ERROR
import com.jfmr.ac.test.tests.data.Network.getRemoteError
import com.jfmr.ac.test.tests.episodes.EpisodeUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemoteEpisodesDataSourceImplTest {

    private val rickAndMortyApiService: RickAndMortyAPI = mockk()
    private val remoteEpisodesDataSourceImpl: RemoteEpisodesDataSource =
        RemoteEpisodesDataSourceImpl(rickAndMortyApiService)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun retrieveEpisodes_Success_ListEpisodeResponse() = runTest {
        val episodes = EpisodeUtils.episodesResponse

        coEvery {
            rickAndMortyApiService.episodes(any())
        } returns Result.success(episodes.toList())

        val actual: Result<List<EpisodeResponse?>?> =
            remoteEpisodesDataSourceImpl.retrieveEpisodes(emptyList())

        assertEquals(episodes.toList(), actual.getOrNull())
    }

    @Test
    fun retrieveEpisodes_Error_RemoteError() = runTest {

        coEvery {
            rickAndMortyApiService.episodes(any())
        } returns getRemoteError(NETWORK_CODE_SERVER_ERROR)

        val actual: Result<List<EpisodeResponse?>?> =
            remoteEpisodesDataSourceImpl.retrieveEpisodes(emptyList())

        assertTrue { actual.isFailure }

    }
}
