package com.jfmr.ac.test.data.remote.character

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.character.CharacterConstants.CHARACTER_BY_ID_SUCCESS
import com.jfmr.ac.test.data.remote.character.CharacterConstants.CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES
import com.jfmr.ac.test.data.remote.character.CharacterConstants.EMPTY
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.TestUtils
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_BAD_REQUEST
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_NOT_FOUND
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_SERVER_ERROR
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_UNAUTHORIZED
import com.jfmr.ac.test.tests.data.Network.getResponseError
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
        val expected: CharacterResponse = TestUtils.getObjectFromJson(CHARACTER_BY_ID_SUCCESS, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val actual: CharacterResponse? = characterRemoteDataSourceImpl.retrieveCharacterById(characterId).body()

        assertEquals(expected, actual)
        assertTrue { actual?.episode?.isNotEmpty() == true }
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmpty_Character() = runTest {
        val expected: CharacterResponse = TestUtils.getObjectFromJson(EMPTY, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val actual: CharacterResponse? = characterRemoteDataSourceImpl.retrieveCharacterById(characterId).body()

        assertEquals(expected, actual)
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessEmptyEpisodes_Character() = runTest {
        val expected: CharacterResponse =
            TestUtils.getObjectFromJson(CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES, CharacterResponse::class.java) as CharacterResponse

        mockSuccess(expected)

        val actual: CharacterResponse? = characterRemoteDataSourceImpl.retrieveCharacterById(characterId).body()

        assertEquals(expected, actual)
        assertTrue { actual?.episode?.isEmpty() == true }
    }

    @org.junit.Test
    fun retrieveCharacterById_SuccessNull_RemoteError() = runTest {
        mockSuccess(null)

        val actual: Response<CharacterResponse> = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)

        assertEquals(NETWORK_CODE_SERVER_ERROR, actual.code())
    }

    private fun mockSuccess(body: CharacterResponse?) {
        body?.let {
            coEvery {
                rickAndMortyApiService.retrieveCharacterById(characterId)
            } returns Response.success(it)

        } ?: kotlin.run {
            coEvery {
                rickAndMortyApiService.retrieveCharacterById(characterId)
            } returns getResponseError(NETWORK_CODE_SERVER_ERROR)
        }

    }

    @org.junit.Test
    fun retrieveCharacterById_Error_Server() = runTest {

        mockErrorResponse(NETWORK_CODE_NOT_FOUND)
        var result: Response<CharacterResponse> = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertEquals(NETWORK_CODE_NOT_FOUND, result.code())

        mockErrorResponse(NETWORK_CODE_BAD_REQUEST)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertEquals(NETWORK_CODE_BAD_REQUEST, result.code())

        mockErrorResponse(NETWORK_CODE_SERVER_ERROR)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertEquals(NETWORK_CODE_SERVER_ERROR, result.code())

        mockErrorResponse(NETWORK_CODE_UNAUTHORIZED)
        result = characterRemoteDataSourceImpl.retrieveCharacterById(characterId)
        assertEquals(NETWORK_CODE_UNAUTHORIZED, result.code())

    }

    @org.junit.Test
    fun getNetworkService() {
        val service = characterRemoteDataSourceImpl.getNetworkService()
        assertEquals(rickAndMortyApiService, service)
    }

    private fun mockErrorResponse(errorCode: Int) {
        coEvery {
            rickAndMortyApiService.retrieveCharacterById(characterId)
        } returns getResponseError(errorCode)
    }
}
