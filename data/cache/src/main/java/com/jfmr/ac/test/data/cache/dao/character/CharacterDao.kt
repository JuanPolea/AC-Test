package com.jfmr.ac.test.data.cache.dao.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfmr.ac.test.data.cache.entities.LocalCharacter

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<LocalCharacter>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: LocalCharacter): Long

    @Query("SELECT * FROM characters")
    fun characters(): PagingSource<Int, LocalCharacter>

    @Query("DELETE FROM characters")
    suspend fun deleteDomainCharacter()

    @Query("SELECT * FROM characters WHERE characters.id = :id")
    suspend fun getCharacterById(id: Int): LocalCharacter?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCharacter(character: LocalCharacter): Int
}
