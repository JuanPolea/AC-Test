package com.jfmr.ac.test.data.cache.entities.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val repoId: Long,
    val prevKey: String?,
    val nextKey: String,
)
