package com.fandiaspraja.pokemon.core.data.source.remote

import android.util.Log
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiResponse
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiService
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonDetailResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonListResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getPokemons(limit: Int, offset: Int) : Flow<ApiResponse<PokemonListResponse>> {
        return flow {
            try {
                val response = apiService.getPokemons(limit = limit, offset = offset)
                response.results?.let {
                    if (it.isNotEmpty()) {
                        emit(ApiResponse.Success(response))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonDetail(name: String) : Flow<ApiResponse<PokemonDetailResponse>> {
        return flow {
            try {
                val response = apiService.getPokemonDetail(name = name)

                Log.d("RemoteDataSource", "getPokemonDetail: $response")


                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}