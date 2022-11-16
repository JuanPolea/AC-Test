package com.jfmr.ac.test.data.repository.episode

import arrow.core.Either
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.repository.utils.LocalUtils.expectedLocalEpisodes
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.data.Network.getResponseError
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.episodesResponse
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.expectedEpisodeList
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeRepositoryImplTest {

    private val remoteEpisodesDataSource: RemoteEpisodesDataSource = mockk()
    private val episodeDao: EpisodeDao = mockk()
    private val episodeRepositoryImpl =
        EpisodeRepositoryImpl(remoteEpisodesDataSource = remoteEpisodesDataSource, episodeDao = episodeDao)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
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
        } returns Response.success(episodesResponse.toList())

        coEvery {
            episodeDao.episodes(emptyList())
        } returns expectedLocalEpisodes.toList()

        val actual = episodeRepositoryImpl.episodes(emptyList()).first()

        actual.fold({

        }, {
            assertEquals(expectedEpisodeList.toList(), it)
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

        val actual: Flow<Either<DomainError, List<Episode>>> = episodeRepositoryImpl.episodes(emptyList())

        actual.collectLatest {
            it.fold({}, {
                assertEquals(expectedEpisodeList.toList(), it)
            })
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

        val actual: Flow<Either<DomainError, List<Episode>>> = episodeRepositoryImpl.episodes(emptyList())

        actual.collectLatest {
            it.fold({
                assertEquals(RemoteError.Connectivity, it)
            }, {})
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
