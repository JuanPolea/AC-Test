package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys
import com.jfmr.ac.test.utils.LocalUtils.expectedLocalCharacter
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

private const val INSERT_SUCCESS = 1
private const val INSERT_ERROR = -1
private const val UPDATE_SUCCESS = 1L
private const val UPDATE_ERROR = -1L

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCharacterDataSourceImplTest {

    private val rickAndMortyDB: RickAndMortyDB = mockk()

    @Inject
    var localCharacterDataSource = LocalCharacterDataSourceImpl(rickAndMortyDB)

    private val localCharacters = mutableListOf<LocalCharacter>()
    private val remoteKeys = mutableListOf<RemoteKeys>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localCharacters.add(expectedLocalCharacter)
        repeat(10) {
            remoteKeys.add(RemoteKeys(it.toLong(), null, "https://rickandmortyapi.com/api/character/?page=2"))
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun geLocalDB() {
        assertEquals(rickAndMortyDB, localCharacterDataSource.geLocalDB())
    }

    @Test
    fun getCharacters() = runTest {
        val expected = PagingSourceUtils(localCharacters)

        coEvery {
            rickAndMortyDB.characterDao().characters()
        } returns expected

        val actual: PagingSource<Int, LocalCharacter> = localCharacterDataSource.getCharacters()

        assertEquals(expected, actual)

    }

    @Test
    fun getCharacterById() = runTest {

        coEvery {
            rickAndMortyDB.characterDao().getCharacterById(any())
        } returns expectedLocalCharacter

        val actual = localCharacterDataSource.getCharacterById(INSERT_SUCCESS)

        assertEquals(expectedLocalCharacter, actual)

    }

    @Test
    fun updateCharacter() = runTest {

        var expected = INSERT_SUCCESS
        assertUpdateCharacter(expected)

        expected = INSERT_ERROR
        assertUpdateCharacter(expected)
    }


    @Test
    fun insert() = runTest {
        var expected = UPDATE_SUCCESS
        assertInsertCharacter(expected)

        expected = UPDATE_ERROR
        assertInsertCharacter(expected)
    }


    @Test
    fun insertCharacters() = runTest {

        val expected = mutableListOf<Long>()

        localCharacters.map {
            expected.add(UPDATE_SUCCESS)
        }

        assertInsertCharacters(expected)

        expected.clear()
        localCharacters.map {
            expected.add(UPDATE_ERROR)
        }
        assertInsertCharacters(expected)

    }


    @Test
    fun insertRemoteKeys() = runTest {

        val expected = remoteKeys.map { it.repoId }
        coEvery {
            rickAndMortyDB.remoteKeysDao().insertAll(remoteKeys)
        } returns expected

        val actual = localCharacterDataSource.insertRemoteKeys(remoteKeys)

        assertEquals(expected, actual)

    }


    @Test
    fun remoteKeysId() = runTest {
        val expected = remoteKeys.first()

        coEvery {
            rickAndMortyDB.remoteKeysDao().remoteKeysId(any())
        } returns expected

        val actual = localCharacterDataSource.remoteKeysId(1L)

        assertEquals(expected, actual)

    }

    private suspend fun assertUpdateCharacter(expected: Int) {
        coEvery {
            rickAndMortyDB.characterDao().updateCharacter(expectedLocalCharacter)
        } returns expected
        val actual = localCharacterDataSource.updateCharacter(expectedLocalCharacter)
        assertEquals(expected, actual)
    }

    private suspend fun assertInsertCharacter(expected: Long) {
        coEvery {
            rickAndMortyDB.characterDao().insert(expectedLocalCharacter)
        } returns expected
        val actual = localCharacterDataSource.insert(expectedLocalCharacter)
        assertEquals(expected, actual)
    }

    private suspend fun assertInsertCharacters(expected: MutableList<Long>) {
        coEvery {
            rickAndMortyDB.characterDao().insertCharacters(localCharacters)
        } returns expected

        val actual = localCharacterDataSource.insertCharacters(localCharacters)

        assertEquals(expected, actual)

    }
}
