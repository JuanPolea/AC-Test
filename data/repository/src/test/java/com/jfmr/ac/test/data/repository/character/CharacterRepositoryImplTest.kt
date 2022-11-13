package com.jfmr.ac.test.data.repository.character

import androidx.paging.map
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharactersResponse
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.toDomain
import com.jfmr.ac.test.data.paging.mapper.CharacterExtensions.toEntity
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.TestUtils
import com.jfmr.ac.test.tests.data.Network.getResponseError
import com.jfmr.ac.test.tests.data.PagingSourceUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryImplTest {

    private val characterRemoteDataSource: CharacterRemoteDataSource = mockk()
    private val localCharacterDataSource: LocalCharacterDataSource = mockk()
    private val characterRepository = CharacterRepositoryImpl(characterRemoteDataSource, localCharacterDataSource)
    private lateinit var characteResponse: CharacterResponse
    private lateinit var characterResponse: CharactersResponse
    private lateinit var localCharacters: List<LocalCharacter>
    private lateinit var localCharacter: LocalCharacter
    private lateinit var character: Character
    private lateinit var characters: List<Character>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterResponse = TestUtils.getObjectFromJson("characters.json", CharactersResponse::class.java) as CharactersResponse
        characteResponse = TestUtils.getObjectFromJson("character.json", CharacterResponse::class.java) as CharacterResponse
        localCharacter = TestUtils.getObjectFromJson("character.json", LocalCharacter::class.java) as LocalCharacter
        character = TestUtils.getObjectFromJson("character.json", Character::class.java) as Character
        localCharacters = characterResponse.results?.filterNotNull()?.map { it.toEntity() } ?: emptyList()
        characters = localCharacters.map { it.toDomain() }
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
        } returns Response.success(characteResponse)

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns localCharacter

        coEvery {
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1)
        actual.fold(
            {

            }, {
                assertEquals(character, it)
            }
        )
    }

    @Test
    fun getCharacterById_Error_LocalCharacter() = runTest {
        coEvery {
            characterRemoteDataSource.retrieveCharacterById(any())
        } returns getResponseError(401)

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns localCharacter

        coEvery {
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1)
        actual.fold(
            {

            }, {
                assertEquals(character, it)
            }
        )
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
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns 1

        val actual = characterRepository.getCharacterById(1)
        actual.fold(
            {
                assertEquals(RemoteError.Connectivity, it)
            }, {}
        )
    }

    @Test
    fun updateCharacter_Success_Character() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns 1

        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns localCharacter

        val actual = characterRepository.updateCharacter(character)

        assertEquals(character, actual)

    }

    @Test
    fun updateCharacter_Error_Character() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns -1
        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns null
        val actual = characterRepository.updateCharacter(character)
        assertEquals(character, actual)

    }

    @Test
    fun updateCharacter_Error_Error() = runTest {
        coEvery {
            localCharacterDataSource.updateCharacter(localCharacter)
        } returns -1
        coEvery {
            localCharacterDataSource.getCharacterById(any())
        } returns null
        val actual = characterRepository.updateCharacter(character)
        assertEquals(character, actual)

    }
}
