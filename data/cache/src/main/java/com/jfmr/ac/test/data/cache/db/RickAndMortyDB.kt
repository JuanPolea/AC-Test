package com.jfmr.ac.test.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfmr.ac.test.data.cache.dao.character.CharacterConverter
import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.dao.character.RemoteKeysDao
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeConverter
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys

private const val DBNAME = "rick_and_morty_database"

@Database(
    entities = [LocalCharacter::class, RemoteKeys::class, LocalEpisode::class],
    version = 1,
)

@TypeConverters(
    CharacterConverter::class,
    EpisodeConverter::class,
)
abstract class RickAndMortyDB : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun episodesDao(): EpisodeDao

    companion object {
        fun create(context: Context): RickAndMortyDB {

            return Room
                .databaseBuilder(
                    context,
                    RickAndMortyDB::class.java,
                    DBNAME
                ).fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
        }
    }
}
