package com.jfmr.ac.test.data.cache.db.character

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin

class CharacterConverter {
    @TypeConverter
    fun domainCharacterToJson(domainCharacter: DomainCharacter): String =
        Gson().toJson(domainCharacter)

    @TypeConverter
    fun jsonToCharacter(string: String): DomainCharacter =
        Gson().fromJson(string, DomainCharacter::class.java)

    @TypeConverter
    fun originToJson(origin: Origin): String =
        Gson().toJson(origin)

    @TypeConverter
    fun jsonToOrigin(string: String): Origin =
        Gson().fromJson(string, Origin::class.java)

    @TypeConverter
    fun locationToJson(location: Location): String =
        Gson().toJson(location)

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
