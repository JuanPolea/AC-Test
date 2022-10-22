package com.jfmr.ac.test.data.cache.dao.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jfmr.ac.test.domain.model.character.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterEntity(characterEntity: CharacterEntity): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharactersEntity(characterEntity: List<CharacterEntity>): List<Long>


    @Query("SELECT * FROM characters")
    fun getCharacterEntity(): Flow<CharacterEntity>

    @Query("SELECT * FROM characters")
    fun getPaginatedCharacterEntity(): PagingSource<Int, CharacterEntity>

    @Transaction
    suspend fun updateCharacterEntity(CharacterEntity: CharacterEntity): Long {
        CharacterEntity.let {
            deleteCharacterEntity()
            return insertCharacterEntity(it)
        }
    }

    @Query("DELETE FROM characters")
    suspend fun deleteCharacterEntity()
}
