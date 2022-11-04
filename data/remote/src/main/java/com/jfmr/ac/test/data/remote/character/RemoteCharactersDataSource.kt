package com.jfmr.ac.test.data.remote.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.open.rickandmorty.character.datasource.CharactersDataSource
import com.jfmr.ac.test.data.open.rickandmorty.character.model.CharacterDetailResponse
import com.jfmr.ac.test.data.remote.network.RickAndMortyApiService
import com.jfmr.ac.test.data.remote.network.paging.RickAndMortyRemoteMediator
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import com.jfmr.ac.test.domain.model.error.RemoteError
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteCharactersDataSource @Inject constructor(
    private val remoteService: RickAndMortyApiService,
    private val rickAndMortyDB: RickAndMortyDB,
) : CharactersDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchCharacters(): Flow<PagingData<DomainCharacter>> {
        return Pager(config = PagingConfig(pageSize = 10),
            remoteMediator = RickAndMortyRemoteMediator(rickAndMortyDB, remoteService),
            pagingSourceFactory = { rickAndMortyDB.characterDao().characters() }).flow
    }

    override suspend fun retrieveCharacterDetail(characterId: Int): DomainResult<DomainCharacter> {
        try {
            val response: Response<CharacterDetailResponse> = remoteService.retrieveCharacterById(characterId)
            if (response.isSuccessful) {
                val remote: DomainCharacter = response.body().toDomain()
                val local = rickAndMortyDB.characterDao().getCharacterById(characterId)
                if (local != null) {
                    rickAndMortyDB.characterDao().updateCharacter(remote.copy(isFavorite = local.isFavorite))
                } else {
                    rickAndMortyDB.characterDao().insert(remote)
                }
            }
            return rickAndMortyDB.characterDao().getCharacterById(characterId)?.right() ?: RemoteError.Connectivity.left()
        } catch (e: Exception) {
            val error = when (e) {
                is IOException -> RemoteError.Connectivity
                is HttpException -> RemoteError.Server(e.code())
                else -> RemoteError.Unknown(e.message.orEmpty())
            }
            return rickAndMortyDB.characterDao().getCharacterById(characterId)?.right() ?: error.left()
        }
    }

    override suspend fun updateCharacter(character: DomainCharacter): DomainCharacter {
        rickAndMortyDB.characterDao().updateCharacter(character)
        return rickAndMortyDB.characterDao().getCharacterById(character.id) ?: character
    }

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Origin?.toDomain() = Origin(
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty(),
    )

    private fun com.jfmr.ac.test.data.open.rickandmorty.character.model.Location?.toDomain() = Location(
        name = this?.name,
        url = this?.url,
    )

    private fun CharacterDetailResponse?.toDomain() = DomainCharacter(
        id = this?.id ?: -1,
        image = this?.image.orEmpty(),
        gender = this?.gender.orEmpty(),
        species = this?.species.orEmpty(),
        created = this?.created.orEmpty(),
        origin = this?.origin?.toDomain(),
        name = this?.name.orEmpty(),
        location = this?.location?.toDomain(),
        episode = this?.episode.orEmpty() as List<String>,
        type = this?.type.orEmpty(),
        url = this?.url.orEmpty(),
        status = this?.status.orEmpty(),
    )

}
