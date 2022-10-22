package com.jfmr.ac.test.data.cache.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin

class DomainCharacterConverter {
    @TypeConverter
    fun domainCharacterToJson(DomainCharacter: DomainCharacter): String =
        Gson().toJson(DomainCharacter)

    @TypeConverter
    fun jsonToCharacter(string: String): DomainCharacter =
        Gson().fromJson(string, DomainCharacter::class.java)

    @TypeConverter
    fun originToJson(Origin: Origin): String =
        Gson().toJson(Origin)

    @TypeConverter
    fun jsonToOrigin(string: String): Origin =
        Gson().fromJson(string, Origin::class.java)

    @TypeConverter
    fun locationToJson(Location: Location): String =
        Gson().toJson(Location)

    @TypeConverter
    fun jsonToLocation(string: String): Location =
        Gson().fromJson(string, Location::class.java)

    @TypeConverter
    fun listStringToJson(stringList: List<String>): String =
        Gson().toJson(stringList)

    @TypeConverter
    fun jsonToListString(string: String): List<String> =
        Gson().fromJson(string, Array<String>::class.java).toList()

    @TypeConverter
    fun characterListToJson(stringList: List<DomainCharacter>): String =
        Gson().toJson(stringList)

    @TypeConverter
    fun jsonToCharacterList(string: String): List<DomainCharacter> =
        Gson().fromJson(string, Array<DomainCharacter>::class.java).toMutableList()
}
