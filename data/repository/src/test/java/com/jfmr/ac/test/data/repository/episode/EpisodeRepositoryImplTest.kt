package com.jfmr.ac.test.data.repository.episode

import arrow.core.Either
import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.TestUtils
import com.jfmr.ac.test.tests.data.Network.getResponseError
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
class EpisodeRepositoryImplTest {

    private val remoteEpisodesDataSource: RemoteEpisodesDataSource = mockk()
    private val episodeDao: EpisodeDao = mockk()
    private val episodeRepositoryImpl = EpisodeRepositoryImpl(
        remoteEpisodesDataSource = remoteEpisodesDataSource,
        episodeDao = episodeDao
    )

    private lateinit var episodeResponse: Array<EpisodeResponse>
    private lateinit var localEpisodes: Array<LocalEpisode>
    private lateinit var episodes: Array<Episode>
    private lateinit var character: Character


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        episodes = TestUtils.getObjectFromJson("episodes.json", Array<Episode>::class.java) as Array<Episode>
        localEpisodes = TestUtils.getObjectFromJson("episodes.json", Array<LocalEpisode>::class.java) as Array<LocalEpisode>
        episodeResponse = TestUtils.getObjectFromJson("episodes.json", Array<EpisodeResponse>::class.java) as Array<EpisodeResponse>
        character = TestUtils.getObjectFromJson("character.json", Character::class.java) as Character

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun episodes_Success_Episodes() = runTest {
        coEvery {
            remoteEpisodesDataSource
                .retrieveEpisodes(emptyList())
        } returns Response.success(episodeResponse.toList())

        coEvery {
            episodeDao.insertEpisodes(localEpisodes.toList())
        } returns arrayListOf()

        coEvery {
            episodeDao.episodes(emptyList())
        } returns localEpisodes.toList()

        val actual: Either<DomainError, List<Episode>> = episodeRepositoryImpl.episodes(emptyList())

        actual.fold(
            {

            }, {
                assertEquals(episodes.toList(), it)
            }
        )
    }

    @Test
    fun episodes_Error_Episodes() = runTest {
        coEvery {
            remoteEpisodesDataSource
                .retrieveEpisodes(emptyList())
        } returns getResponseError()

        coEvery {
            episodeDao.insertEpisodes(localEpisodes.toList())
        } returns arrayListOf()

        coEvery {
            episodeDao.episodes(emptyList())
        } returns localEpisodes.toList()

        val actual: Either<DomainError, List<Episode>> = episodeRepositoryImpl.episodes(emptyList())

        actual.fold(
            {
            }, {
                assertEquals(episodes.toList(), it)
            }
        )
    }

    @Test
    fun episodes_Error_Error() = runTest {
        coEvery {
            remoteEpisodesDataSource
                .retrieveEpisodes(emptyList())
        } returns getResponseError()

        coEvery {
            episodeDao.insertEpisodes(localEpisodes.toList())
        } returns arrayListOf()

        coEvery {
            episodeDao.episodes(emptyList())
        } returns null

        val actual: Either<DomainError, List<Episode>> = episodeRepositoryImpl.episodes(emptyList())

        actual.fold(
            {
                assertEquals(RemoteError.Connectivity, it)
            }, {
            }
        )
    }

    @Test
    fun insert() = runTest {

        val expectedEpisodes = Episodes(
            results = episodes.toList()
        )

        coEvery {
            episodeDao.insertEpisodes(localEpisodes.toList())
        } returns listOf()

        val actual = episodeRepositoryImpl.insert(expectedEpisodes)

        assertEquals(listOf(), actual)
    }
}
