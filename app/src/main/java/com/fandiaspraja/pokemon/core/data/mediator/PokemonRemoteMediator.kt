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

    override suspend fun initialize(): InitializeAction {
        return if (dao.getPokemonCount() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    if (dao.getPokemonCount() == 0) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }

                    val last = state.lastItemOrNull()
                    if (last == null) {
                        // ✅ Ambil nextKey dari DB dan langsung pakai untuk fetch
                        val lastKey = keysDao.getLastRemoteKey()
                        lastKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        // Normal flow
                        keysDao.remoteKeys(last.id)?.nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
//                LoadType.APPEND -> {
//                    // ✅ Query langsung ke DB, bukan dari PagingState
//                    val remoteKey = db.withTransaction {
//                        keysDao.getLastRemoteKey()  // ambil key terakhir dari DB
//                    }
//
//                    Log.d("Mediator", "APPEND: lastRemoteKey=$remoteKey")
//
//                    remoteKey?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                LoadType.APPEND -> {
//
//
//                    val last = state.lastItemOrNull()
//                    if (last == null) {
//                        Log.d("Mediator", "APPEND: lastItem null → stop")
//                        return MediatorResult.Success(true)
//                    }
//
//                    val remoteKey = keysDao.remoteKeys(last.id)
//                    Log.d("Mediator", "APPEND: last.id=${last.id}, remoteKey=$remoteKey")
//
//                    if (remoteKey?.nextKey == null) {
//                        Log.d("Mediator", "APPEND: nextKey null → stop")
//                        return MediatorResult.Success(true)
//                    }
//
//                    remoteKey.nextKey
//                }
            }

            Log.d("Mediator", "loadType=$loadType, page=$page")  // ← dan ini

            val response = api.getPokemons(
                limit = state.config.pageSize,
                offset = page
            )

            Log.d("Mediator", "response.results=${response.results.size}")
            Log.d("Mediator", "sample url=${response.results.firstOrNull()?.url}")


            val endOfPagination = response.next == null

            Log.d("Mediator", "before transaction")


            db.withTransaction {
                Log.d("Mediator", "inside transaction")

                if (loadType == LoadType.REFRESH) {
                    dao.clearAllPokemon()
                    keysDao.clearRemoteKeys()
                }

                // Use mapNotNull to create a List<PokemonEntity> and filter out any nulls
                val entities = response.results.mapIndexedNotNull { index, item ->
                    val id = item.url?.trimEnd('/')?.split("/")?.last()?.toIntOrNull()
                    id?.let {
                        PokemonEntity(
                            id = it,
                            name = item.name ?: "Unknown",
                            url = item.url ?: "",
                            index = page + index   // ✅ index unik per item
                        )
                    }
                }

                Log.d("Mediator", "entities saved: ${entities.size}")

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
                    Log.d("Mediator", "insert done")
                }

            }

            // Tambah ini
            val count = dao.getPokemonCount()
            Log.d("Mediator", "count after insert: $count")

            MediatorResult.Success(endOfPagination)

        } catch (e: Exception) {
            Log.e("Mediator", "error: ${e.message}", e)  // pastikan ini ada
            MediatorResult.Error(e)
        }
    }
}