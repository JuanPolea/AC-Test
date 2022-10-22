package com.jfmr.ac.test.data.cache.dao.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDomainCharacter(DomainCharacter: DomainCharacter): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharactersEntity(DomainCharacter: List<DomainCharacter>): List<Long>


    @Query("SELECT * FROM characters")
    fun getDomainCharacter(): Flow<DomainCharacter>

    @Query("SELECT * FROM characters")
    fun getPaginatedDomainCharacter(): PagingSource<Int, DomainCharacter>

    @Transaction
    suspend fun updateDomainCharacter(DomainCharacter: DomainCharacter): Long {
        DomainCharacter.let {
            deleteDomainCharacter()
            return insertDomainCharacter(it)
        }
    }

    @Query("DELETE FROM characters")
    suspend fun deleteDomainCharacter()
}
