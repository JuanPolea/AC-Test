package com.jfmr.ac.test.data.cache.dao.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfmr.ac.test.data.cache.entities.LocalEpisode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<LocalEpisode>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: LocalEpisode): Long

    @Query("SELECT * FROM episodes WHERE episodes.id IN (:episodeIds)")
    suspend fun episodes(episodeIds: List<Int>): List<LocalEpisode>?

    @Query("DELETE FROM episodes")
    suspend fun deleteDomainEpisode()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEpisode(episode: LocalEpisode): Int
}
