package com.jfmr.ac.test.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.dao.character.RemoteKeysDao
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.RemoteKeys

private const val DBNAME = "rick_and_morty_database"

@Database(
    entities = [DomainCharacter::class, RemoteKeys::class],
    version = 1,
)

@TypeConverters(
    DomainCharacterConverter::class
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
