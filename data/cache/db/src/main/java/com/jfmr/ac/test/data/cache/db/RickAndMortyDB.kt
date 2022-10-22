package com.jfmr.ac.test.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.dao.character.RemoteKeysDao
import com.jfmr.ac.test.domain.model.character.CharacterEntity
import com.jfmr.ac.test.domain.model.character.RemoteKeys
import entities.CharacterEntityConverter

private const val DBNAME = "rick_and_morty_database"

@Database(
    entities = [CharacterEntity::class, RemoteKeys::class],
    version = 1,
)

@TypeConverters(
    CharacterEntityConverter::class
)
abstract class RickAndMortyDB : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

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
