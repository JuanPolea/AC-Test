package com.jfmr.ac.test.data.cache.dao.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfmr.ac.test.domain.model.character.DomainCharacter

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<DomainCharacter>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DomainCharacter): Long

    @Query("SELECT * FROM characters")
    fun characters(): PagingSource<Int, DomainCharacter>

    @Query("DELETE FROM characters")
    suspend fun deleteDomainCharacter()

    @Query("SELECT * FROM characters WHERE characters.id = :id")
    suspend fun getCharacterById(id: Int): DomainCharacter?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCharacter(character: DomainCharacter): Int
}
