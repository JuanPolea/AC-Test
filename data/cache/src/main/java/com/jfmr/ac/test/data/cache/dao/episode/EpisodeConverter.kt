package com.jfmr.ac.test.data.cache.dao.episode

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes

class EpisodeConverter {
    @TypeConverter
    fun domainEpisodesToJson(episodes: List<DomainEpisode>): String =
        Gson().toJson(episodes)

    @TypeConverter
    fun jsonToEpisodes(string: String): Array<Episodes> =
        Gson().fromJson(string, Array<Episodes>::class.java)

    @TypeConverter
    fun domainEpisodeToJson(episode: DomainEpisode): String =
        Gson().toJson(episode)

    @TypeConverter
    fun jsonToEpisode(string: String): DomainEpisode =
        Gson().fromJson(string, DomainEpisode::class.java)
}
