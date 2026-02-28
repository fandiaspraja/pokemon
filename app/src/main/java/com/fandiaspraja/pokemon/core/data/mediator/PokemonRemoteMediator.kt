package com.fandiaspraja.pokemon.core.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.fandiaspraja.pokemon.core.utils.DataMapper
import com.fandiaspraja.pokemon.core.data.source.local.LocalDataSource
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.remote.RemoteDataSource
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.flow.first
import kotlin.collections.emptyList

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        val offset = when (loadType) {

            LoadType.REFRESH -> 0

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()

                if (lastItem == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                lastItem.index + 1
            }

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        Log.d("PokemonMediator", "➡️ loadType: $loadType, offset: $offset")

        return try {

            val response = remote.getPokemons(
                limit = state.config.pageSize,
                offset = offset
            ).first()

            val apiResult = when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Empty -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                is ApiResponse.Error -> throw Exception(response.errorMessage)
            }

            val entities = DataMapper.mapListResponseToEntityPokemons(
                apiResult.results,
                offset
            )

            if (loadType == LoadType.REFRESH) {
                local.clearAllPokemon()
            }

            local.insertAllPokemon(entities)

            val endOfPagination = apiResult.next == null

            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )

        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}