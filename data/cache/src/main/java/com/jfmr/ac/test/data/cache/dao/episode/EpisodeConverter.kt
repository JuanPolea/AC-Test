package com.jfmr.ac.test.data.cache.dao.episode

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.data.cache.entities.LocalEpisode

class EpisodeConverter {
    @TypeConverter
    fun domainEpisodesToJson(episodes: List<LocalEpisode>): String =
        Gson().toJson(episodes)

    @TypeConverter
    fun jsonToEpisodes(string: String): Array<LocalEpisode> =
        Gson().fromJson(string, Array<LocalEpisode>::class.java)

    @TypeConverter
    fun domainEpisodeToJson(episode: LocalEpisode): String =
        Gson().toJson(episode)

    @TypeConverter
    fun jsonToEpisode(string: String): LocalEpisode =
        Gson().fromJson(string, LocalEpisode::class.java)
}
