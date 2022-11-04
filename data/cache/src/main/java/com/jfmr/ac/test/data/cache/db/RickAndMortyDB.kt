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
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.RemoteKeys
import com.jfmr.ac.test.domain.model.episode.DomainEpisode

private const val DBNAME = "rick_and_morty_database"

@Database(
    entities = [DomainCharacter::class, RemoteKeys::class, DomainEpisode::class],
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

            return Room.databaseBuilder(
                context,
                RickAndMortyDB::class.java,
                DBNAME
            )
                .build()
        }
    }
}
