package entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jfmr.ac.test.domain.model.character.CharacterEntity
import com.jfmr.ac.test.domain.model.character.LocationEntity
import com.jfmr.ac.test.domain.model.character.OriginEntity

class CharacterEntityConverter {
    @TypeConverter
    fun characterEntityToJson(characterEntity: CharacterEntity): String =
        Gson().toJson(characterEntity)

    @TypeConverter
    fun jsonToCharacter(string: String): CharacterEntity =
        Gson().fromJson(string, CharacterEntity::class.java)

    @TypeConverter
    fun originEntityToJson(originEntity: OriginEntity): String =
        Gson().toJson(originEntity)

    @TypeConverter
    fun jsonToOriginEntity(string: String): OriginEntity =
        Gson().fromJson(string, OriginEntity::class.java)

    @TypeConverter
    fun locationEntityToJson(locationEntity: LocationEntity): String =
        Gson().toJson(locationEntity)

    @TypeConverter
    fun jsonToLocationEntity(string: String): LocationEntity =
        Gson().fromJson(string, LocationEntity::class.java)

    @TypeConverter
    fun listToJson(stringList: List<String>): String =
        Gson().toJson(stringList)

    @TypeConverter
    fun jsonToList(string: String): List<String> =
        Gson().fromJson(string, Array<String>::class.java).toList()

    @TypeConverter
    fun entityListToJson(stringList: List<CharacterEntity>): String =
        Gson().toJson(stringList)

    @TypeConverter
    fun jsonToEntityList(string: String): List<CharacterEntity> =
        Gson().fromJson(string, Array<CharacterEntity>::class.java).toMutableList()
}
