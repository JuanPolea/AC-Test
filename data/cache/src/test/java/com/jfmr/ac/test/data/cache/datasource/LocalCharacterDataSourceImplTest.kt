package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys
import com.jfmr.ac.test.tests.TestUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

private const val INSERT_SUCCESS = 1
private const val INSERT_ERROR = -1
private const val UPDATE_SUCCESS = 1L
private const val UPDATE_ERROR = -1L

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCharacterDataSourceImplTest {

    private val rickAndMortyDB: RickAndMortyDB = mockk()

    @Inject
    var localCharacterDataSource = LocalCharacterDataSourceImpl(rickAndMortyDB)

    private lateinit var localCharacter: LocalCharacter
    private val localCharacters = mutableListOf<LocalCharacter>()
    private val remoteKeys = mutableListOf<RemoteKeys>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localCharacter = TestUtils.getObjectFromJson("character.json", LocalCharacter::class.java) as LocalCharacter
        localCharacters.add(localCharacter)
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

        val expected = localCharacter

        coEvery {
            rickAndMortyDB.characterDao().getCharacterById(any())
        } returns expected

        val actual = localCharacterDataSource.getCharacterById(INSERT_SUCCESS)

        assertEquals(expected, actual)

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
            rickAndMortyDB.characterDao().updateCharacter(localCharacter)
        } returns expected
        val actual = localCharacterDataSource.updateCharacter(localCharacter)
        assertEquals(expected, actual)
    }

    private suspend fun assertInsertCharacter(expected: Long) {
        coEvery {
            rickAndMortyDB.characterDao().insert(localCharacter)
        } returns expected
        val actual = localCharacterDataSource.insert(localCharacter)
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
