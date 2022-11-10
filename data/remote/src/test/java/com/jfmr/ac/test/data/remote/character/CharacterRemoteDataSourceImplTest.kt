package com.jfmr.ac.test.data.remote.character

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.character.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.character.CharacterConstants.CHARACTER_BY_ID_SUCCESS
import com.jfmr.ac.test.data.remote.character.CharacterConstants.CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES
import com.jfmr.ac.test.data.remote.character.CharacterConstants.EMPTY
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.TestUtils
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_BAD_REQUEST
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_NOT_FOUND
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_SERVER_ERROR
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_UNAUTHORIZED
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import retrofit2.Response
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRemoteDataSourceImplTest {

    private val rickAndMortyApiService: RickAndMortyApiService = mockk()

    @get:Rule
    val instantTaskExecutorRule = MainCoroutineRule()

    @Inject
    val characterRemoteDataSourceImpl = CharacterRemoteDataSourceImpl(rickAndMortyApiService)

    private val characterID = 1

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
        val expected: CharacterResponse =
            TestUtils.getObjectFromJson(CHARACTER_BY_ID_SUCCESS, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val result = characterRemoteDataSourceImpl.retrieveCharacterById(characterID)

        result.fold(
            {},
            {
                assertEquals(expected, it)
                assertTrue { it.episode?.isNotEmpty() == true }
            }
        )
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmpty_Character() = runTest {
        val expected: CharacterResponse =
            TestUtils.getObjectFromJson(EMPTY, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val result = characterRemoteDataSourceImpl.retrieveCharacterById(characterID)

        result.fold(
            {},
            {
                assertEquals(expected, it)
            }
        )
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmptyEpisodes_Character() = runTest {
        val expected: CharacterResponse =
            TestUtils.getObjectFromJson(CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val result = characterRemoteDataSourceImpl.retrieveCharacterById(characterID)

        result.fold(
            {},
            {
                assertEquals(expected, it)
                assertTrue { it.episode?.isEmpty() == true }
            }
        )
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessNull_RemoteError() = runTest {

        mockSuccess(null)

        val result = characterRemoteDataSourceImpl.retrieveCharacterById(characterID)

        result.fold(
            { assertEquals(RemoteError.Connectivity, it) },
            {}
        )
    }

    private fun mockSuccess(body: CharacterResponse?) {
        coEvery {
            rickAndMortyApiService.retrieveCharacterById(characterID)
        } returns Response.success(body)
    }

    @org.junit.Test
    fun retrieveCharacterById_Error_Server() = runTest {
        val characterId = 7

        mockErrorResponse(NETWORK_CODE_NOT_FOUND)
        var result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertError(result, NETWORK_CODE_NOT_FOUND)

        mockErrorResponse(NETWORK_CODE_BAD_REQUEST)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertError(result, NETWORK_CODE_BAD_REQUEST)

        mockErrorResponse(NETWORK_CODE_SERVER_ERROR)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertError(result, NETWORK_CODE_SERVER_ERROR)

        mockErrorResponse(NETWORK_CODE_UNAUTHORIZED)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertError(result, NETWORK_CODE_UNAUTHORIZED)
    }

    private fun assertError(result: DomainResult<CharacterResponse>, code: Int) {
        result.fold(
            { assertEquals(RemoteError.Server(code), it) },
            {}
        )
    }

    private fun mockErrorResponse(errorCode: Int) {
        coEvery {
            rickAndMortyApiService.retrieveCharacterById(7)
        } returns Response.error(errorCode, "ErrorBody".toResponseBody())
    }

    @org.junit.Test
    fun getNetworkService() {
        val service = characterRemoteDataSourceImpl.getNetworkService()
        assertEquals(rickAndMortyApiService, service)
    }
}
