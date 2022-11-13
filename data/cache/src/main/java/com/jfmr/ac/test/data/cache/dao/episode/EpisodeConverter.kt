package com.jfmr.ac.test.data.cache.dao.episode

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode

class EpisodeConverter {

    @TypeConverter
    fun localEpisodeToJson(episode: LocalEpisode): String =
        Gson().toJson(episode)

    @TypeConverter
    fun jsonToLocalEpisode(string: String): LocalEpisode =
        Gson().fromJson(string, LocalEpisode::class.java)

    @TypeConverter
    fun localEpisodesToJson(episodes: List<LocalEpisode>): String =
        Gson().toJson(episodes)

    @TypeConverter
    fun jsonToLocalEpisodes(string: String): Array<LocalEpisode> =
        Gson().fromJson(string, Array<LocalEpisode>::class.java)

}
