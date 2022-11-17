package com.jfmr.ac.test.data.remote.episode

import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_SERVER_ERROR
import com.jfmr.ac.test.tests.data.Network.getResponseError
import com.jfmr.ac.test.tests.episodes.EpisodeUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteEpisodesDataSourceImplTest {

    private val rickAndMortyApiService: RickAndMortyApiService = mockk()
    private val remoteEpisodesDataSourceImpl: RemoteEpisodesDataSource = RemoteEpisodesDataSourceImpl(rickAndMortyApiService)

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
        } returns Response.success(episodes.toList())

        val actual: Response<List<EpisodeResponse?>?> = remoteEpisodesDataSourceImpl.retrieveEpisodes(emptyList())

        assertEquals(episodes.toList(), actual.body())
    }

    @Test
    fun retrieveEpisodes_Error_RemoteError() = runTest {

        coEvery {
            rickAndMortyApiService.episodes(any())
        } returns getResponseError(NETWORK_CODE_SERVER_ERROR)

        val actual: Response<List<EpisodeResponse?>?> = remoteEpisodesDataSourceImpl.retrieveEpisodes(emptyList())

        assertEquals(actual.code(), NETWORK_CODE_SERVER_ERROR)

    }
}
