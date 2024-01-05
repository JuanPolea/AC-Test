package com.jfmr.ac.test.data.repository.character

import androidx.paging.map
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.toDomain
import com.jfmr.ac.test.data.paging.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.data.repository.utils.LocalUtils.expectedLocalCharacter
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacter
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacterResponse
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharactersResponse
import com.jfmr.ac.test.tests.data.Network.getResponseError
import com.jfmr.ac.test.tests.data.PagingSourceUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryImplTest {

    @get:Rule
    var instantExecutor = MainCoroutineRule()

    private val characterRemoteDataSource: CharacterRemoteDataSource = mockk()
    private val localCharacterDataSource: LocalCharacterDataSource = mockk()

    private var coroutineDispatcher: CoroutineDispatcher = spyk(Dispatchers.IO)
    private val characterRepository = CharacterRepositoryImpl(characterRemoteDataSource = characterRemoteDataSource,
        localCharacterDataSource = localCharacterDataSource,
        coroutineDispatcher = coroutineDispatcher)
    private lateinit var localCharacters: List<LocalCharacter>
    private lateinit var characters: List<Character>

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        localCharacters =
            expectedCharactersResponse.results?.asSequence()?.filterNotNull()?.map { it.toEntity() }
                ?.toList() ?: emptyList()
        characters = localCharacters.map { it.toDomain() }

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        every { coroutineDispatcher.fold<Any>(any(), any()) } answers { testDispatcher.fold(firstArg(), secondArg()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun characters() = runTest {
        coEvery {
            localCharacterDataSource.getCharacters()
        } returns PagingSourceUtils(localCharacters.toList())

        val actual = characterRepository.characters()

        actual.map {
            it.map { c ->
                assertContains(characters, c)
            }
        }
    }

    @Test
    fun getCharacterById_Success_Character() = runTest {

        coEvery {
            characterRemoteDataSource.retrieveCharacterById(any())
        } returns Response.success(expectedCharacterResponse)

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns expectedLocalCharacter

        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1).first()
        actual.fold({
        }, {
            assertEquals(expectedCharacter, it)
        })
    }

    @Test
    fun getCharacterById_Error_LocalCharacter() = runTest {
        coEvery {
            characterRemoteDataSource.retrieveCharacterById(any())
        } returns getResponseError(401)

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns expectedLocalCharacter

        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1)
        actual.collectLatest {
            it.fold({

            }, {
                assertEquals(expectedCharacter, it)
            })
        }

    }

    @Test
    fun getCharacterById_Error_Error() = runTest {
        coEvery {
            characterRemoteDataSource.retrieveCharacterById(any())
        } returns getResponseError()

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns null

        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1)
        actual.collectLatest {
            it.fold({
                assertEquals(RemoteError.Connectivity, it)
            }, {})
        }
    }

    @Test
    fun updateCharacter_Success_Character() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns 1

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns expectedLocalCharacter

        val actual = characterRepository.updateCharacter(expectedCharacter)
        actual.collectLatest {
            assertEquals(expectedCharacter, it)
        }
    }

    @Test
    fun updateCharacter_Error_Character() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns -1
        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns null
        val actual = characterRepository.updateCharacter(expectedCharacter)
        actual.collectLatest {
            assertEquals(expectedCharacter, it)
        }
    }

    @Test
    fun updateCharacter_Error_Error() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        } returns -1
        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns null
        val actual = characterRepository.updateCharacter(expectedCharacter).first()
        assertEquals(expectedCharacter, actual)
    }
}
