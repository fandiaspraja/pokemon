package com.fandiaspraja.pokemon.core.domain.usecase

import androidx.paging.PagingData
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun getPokemons(): Flow<PagingData<Pokemon>>

    suspend fun refresh()


    fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail>>

//    user local data
    suspend fun insertUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    suspend fun loginUser(email: String, password: String): User?

//    pokemon local data
    fun getAllPokemons(): Flow<List<Pokemon>>
    fun getPokemonByName(name: String): Flow<Pokemon?>
    suspend fun insertAllPokemon(data: List<Pokemon>)
    suspend fun clearAllPokemon()
}