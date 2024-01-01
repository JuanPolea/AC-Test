package com.jfmr.ac.test.data.cache.dao.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodes: List<LocalEpisode>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(episode: LocalEpisode): Long

    @Query("SELECT * FROM episodes WHERE episodes.id IN (:episodeIds)")
    fun episodes(episodeIds: List<Int>): List<LocalEpisode>?

    @Query("DELETE FROM episodes")
    fun deleteDomainEpisode()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEpisode(episode: LocalEpisode): Int
}
