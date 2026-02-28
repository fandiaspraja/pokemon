package com.fandiaspraja.pokemon.core.data.source.remote.network

import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonDetailResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name : String
    ): PokemonDetailResponse
}