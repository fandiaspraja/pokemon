package com.fandiaspraja.pokemon.core.domain.repository

import androidx.paging.PagingData
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.data.source.local.entity.HeroFavoriteEntity
import com.fandiaspraja.pokemon.core.domain.model.Hero
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

//    remote data source
//    fun getPokemonRemote(limit: Int, offset: Int): Flow<Resource<List<Pokemon>>>

    fun getPokemons(): Flow<PagingData<Pokemon>>

    fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail>>

//    local user
    suspend fun insertUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    suspend fun loginUser(email: String, password: String): User?

//local pokemon
    fun getAllPokemons(): Flow<List<Pokemon>>
    fun getPokemonByName(name: String): Flow<Pokemon?>

    suspend fun insertAllPokemon(data: List<Pokemon>)
    suspend fun clearAllPokemon()

}