package com.jfmr.ac.test.data.cache.dao.character

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin

class CharacterConverter {

    @TypeConverter
    fun domainCharacterToJson(localCharacter: LocalCharacter): String =
        Gson().toJson(localCharacter)

    @TypeConverter
    fun jsonToCharacter(string: String): LocalCharacter =
        Gson().fromJson(string, LocalCharacter::class.java)

    @TypeConverter
    fun originToJson(origin: LocalOrigin): String =
        Gson().toJson(origin)

    @TypeConverter
    fun jsonToOrigin(string: String): LocalOrigin =
        Gson().fromJson(string, LocalOrigin::class.java)

    @TypeConverter
    fun locationToJson(location: LocalLocation): String =
        Gson().toJson(location)

    @TypeConverter
    fun jsonToLocation(string: String): LocalLocation =
        Gson().fromJson(string, LocalLocation::class.java)

    @TypeConverter
    fun listStringToJson(stringList: List<String>): String =
        Gson().toJson(stringList)

    @TypeConverter
    fun jsonToListString(string: String): List<String> =
        Gson().fromJson(string, Array<String>::class.java).toList()

}
