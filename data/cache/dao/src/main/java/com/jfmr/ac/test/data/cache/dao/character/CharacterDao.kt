package com.jfmr.ac.test.data.cache.dao.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfmr.ac.test.domain.model.character.DomainCharacter

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<DomainCharacter>): List<Long>

    @Query("SELECT * FROM characters")
    fun characters(): PagingSource<Int, DomainCharacter>

    @Query("DELETE FROM characters")
    suspend fun deleteDomainCharacter()
}
