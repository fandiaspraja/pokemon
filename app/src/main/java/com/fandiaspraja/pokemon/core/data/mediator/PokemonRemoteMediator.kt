package com.fandiaspraja.pokemon.core.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fandiaspraja.pokemon.core.utils.DataMapper
import com.fandiaspraja.pokemon.core.data.source.local.LocalDataSource
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonRemoteKeys
import com.fandiaspraja.pokemon.core.data.source.local.room.PokemonDatabase
import com.fandiaspraja.pokemon.core.data.source.remote.RemoteDataSource
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiResponse
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiService
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.flow.first
import kotlin.collections.emptyList

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val db: PokemonDatabase,
    private val api: ApiService
) : RemoteMediator<Int, PokemonEntity>() {

    private val dao = db.pokemonDao()
    private val keysDao = db.pokemonRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val last = state.lastItemOrNull()
                        ?: return MediatorResult.Success(true)

                    keysDao.remoteKeys(last.id)?.nextKey
                        ?: return MediatorResult.Success(true)
                }
            }

            val response = api.getPokemons(
                limit = state.config.pageSize,
                offset = page
            )

            val endOfPagination = response.next == null

            db.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    dao.clearAllPokemon()
                    keysDao.clearRemoteKeys()
                }

                // Use mapNotNull to create a List<PokemonEntity> and filter out any nulls
                val entities = response.results.mapNotNull { item ->
                    val id = item.url?.trimEnd('/')?.split("/")?.last()?.toIntOrNull()

                    id?.let {
                        PokemonEntity(
                            id = it,
                            name = item.name ?: "Unknown",
                            url = item.url ?: "",
                            // The index is now derived from the entity's position in the non-null list,
                            // but since your primary key is `id`, this index might not be necessary.
                            // If you rely on it, consider using mapIndexed and then filterNotNull.
                            // For simplicity, we can remove the index dependency if not critical.
                            index = 0 // Or handle index differently if needed
                        )
                    }
                }

                if (entities.isNotEmpty()) {
                    val keys = entities.map {
                        PokemonRemoteKeys(
                            pokemonId = it.id,
                            prevKey = if (page == 0) null else page - state.config.pageSize,
                            nextKey = if (endOfPagination) null else page + state.config.pageSize
                        )
                    }

                    dao.insertAllPokemon(entities)
                    keysDao.insertAll(keys)
                }



//                val entities = response.results.mapIndexed { index, item ->
//                    val id = item.url?.trimEnd('/')?.split("/")?.last()?.toInt()
//
//                    id?.let {
//                        PokemonEntity(
//                            id = it,
//                            name = "${item.name}",
//                            url = "${item.url}",
//                            index = page + index
//                        )
//                    }
//                }
//
//                val keys = entities.map {
//                    PokemonRemoteKeys(
//                        pokemonId = it?.id ?: 0,
//                        prevKey = if (page == 0) null else page - state.config.pageSize,
//                        nextKey = if (endOfPagination) null else page + state.config.pageSize
//                    )
//                }
//
//                dao.insertAllPokemon(entities)
//                keysDao.insertAll(keys)
            }

            MediatorResult.Success(endOfPagination)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}