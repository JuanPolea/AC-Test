package com.jfmr.ac.test.data.cache.dao.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>): List<Long>

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    fun remoteKeysId(repoId: Long): RemoteKeys?

    @Query("DELETE FROM remote_keys WHERE prevKey = :query")
    fun clearRemoteKeys(query: String)
}
