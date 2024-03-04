package com.jfmr.ac.test.data.repository.episode

import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.repository.utils.LocalUtils.expectedLocalEpisodes
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.data.Network.getResponseError
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.episodesResponse
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.expectedEpisodeList
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeRepositoryImplTest {

    @get:Rule
    var instantExecutor = MainCoroutineRule()
    private var coroutineDispatcher: CoroutineDispatcher = spyk(Dispatchers.IO)

    private val remoteEpisodesDataSource: RemoteEpisodesDataSource = mockk()
    private val episodeDao: EpisodeDao = mockk()
    private val episodeRepositoryImpl =
        EpisodeRepositoryImpl(
            remoteEpisodesDataSource = remoteEpisodesDataSource,
            episodeDao = episodeDao,
            coroutineDispatcher = coroutineDispatcher
        )
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        every {
            coroutineDispatcher.fold<Any>(
                any(),
                any()
            )
        } answers { testDispatcher.fold(firstArg(), secondArg()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun episodes_Success_Episodes() = runTest {
        coEvery {
            episodeDao.insertEpisodes(any())
        } returns arrayListOf()
        coEvery {
            remoteEpisodesDataSource.retrieveEpisodes(emptyList())
        } returns Result.success(episodesResponse.toList())

        coEvery {
            episodeDao.episodes(emptyList())
        } returns expectedLocalEpisodes.toList()

        val actual = episodeRepositoryImpl.episodes(emptyList()).first()

        actual.fold({
            assertEquals(expectedEpisodeList.toList(), it)
        }, {
        })

    }

    @Test
    fun episodes_Error_Episodes() = runTest {
        coEvery {
            remoteEpisodesDataSource.retrieveEpisodes(emptyList())
        } returns getResponseError()

        coEvery {
            episodeDao.insertEpisodes(expectedLocalEpisodes.toList())
        } returns arrayListOf()

        coEvery {
            episodeDao.episodes(emptyList())
        } returns expectedLocalEpisodes.toList()

        val actual = episodeRepositoryImpl.episodes(emptyList())

        actual.collectLatest { result ->
            result.fold(
                { list ->
                    assertEquals(expectedEpisodeList.toList(), list)
                },
                {
                }
            )
        }

    }

    @Test
    fun episodes_Error_Error() = runTest {
        coEvery {
            remoteEpisodesDataSource.retrieveEpisodes(emptyList())
        } returns getResponseError()

        coEvery {
            episodeDao.insertEpisodes(expectedLocalEpisodes.toList())
        } returns arrayListOf()

        coEvery {
            episodeDao.episodes(emptyList())
        } returns null

        val actual = episodeRepositoryImpl.episodes(emptyList())

        actual.collectLatest { result ->
            result.fold({
            }, { error ->
                assertEquals("401", error.message)
            })
        }
    }

    @Test
    fun insert() = runTest {

        val expectedEpisodes = Episodes(results = expectedEpisodeList.toList())

        coEvery {
            episodeDao.insertEpisodes(expectedLocalEpisodes.toList())
        } returns listOf()

        val actual = episodeRepositoryImpl.insert(expectedEpisodes)

        assertEquals(listOf(), actual)
    }
}
