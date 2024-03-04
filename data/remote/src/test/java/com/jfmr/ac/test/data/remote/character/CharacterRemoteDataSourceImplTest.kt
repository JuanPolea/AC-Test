package com.jfmr.ac.test.data.remote.character

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.character.CharacterUtils
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_SERVER_ERROR
import com.jfmr.ac.test.tests.data.Network.getRemoteError
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRemoteDataSourceImplTest {

    private val rickAndMortyApiService: RickAndMortyAPI = mockk()
    private val remoteAPIService: RickAndMortyAPI = mockk()

    @get:Rule
    val instantTaskExecutorRule = MainCoroutineRule()

    private val characterRemoteDataSourceImpl =
        CharacterRemoteDataSourceImpl(
            ktorService = remoteAPIService
        )

    private val characterId = 1

    @org.junit.Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @org.junit.After
    fun tearDown() {
        clearAllMocks()
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessData_Character() = runTest {
        val expected: CharacterResponse = CharacterUtils.expectedCharacterResponse

        mockSuccess(expected)

        val actual: CharacterResponse? =
            characterRemoteDataSourceImpl.retrieveCharacterById(characterId).getOrNull()

        assertEquals(expected, actual)
        assertTrue { actual?.episode?.isNotEmpty() == true }
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmpty_Character() = runTest {
        val expected: CharacterResponse = CharacterUtils.expectedEmptyCharacterResponse

        mockSuccess(expected)

        val actual: CharacterResponse? =
            characterRemoteDataSourceImpl.retrieveCharacterById(characterId).getOrNull()

        assertEquals(expected, actual)
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmptyEpisodes_Character() = runTest {
        val expected: CharacterResponse = CharacterUtils.expectedCharacterResponseWithoutEpisodes

        mockSuccess(expected)

        val actual: CharacterResponse? =
            characterRemoteDataSourceImpl.retrieveCharacterById(characterId).getOrNull()

        assertEquals(expected, actual)
        assertTrue { actual?.episode?.isEmpty() == true }
    }

    private fun mockSuccess(body: CharacterResponse?) {
        body?.let {
            coEvery {
                rickAndMortyApiService.retrieveCharacterById(characterId)
            } returns Result.success(it)

        } ?: kotlin.run {
            coEvery {
                rickAndMortyApiService.retrieveCharacterById(characterId)
            } returns getRemoteError(NETWORK_CODE_SERVER_ERROR)
        }

        body?.let {
            coEvery {
                remoteAPIService.retrieveCharacterById(characterId)
            } returns Result.success(it)

        } ?: kotlin.run {
            coEvery {
                remoteAPIService.retrieveCharacterById(characterId)
            } returns getRemoteError(NETWORK_CODE_SERVER_ERROR)
        }

    }
}
