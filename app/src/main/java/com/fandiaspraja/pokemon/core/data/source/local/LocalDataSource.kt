package com.fandiaspraja.pokemon.core.data.source.local

import androidx.paging.PagingSource
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.UserEntity
import com.fandiaspraja.pokemon.core.data.source.local.room.PokemonDao
import com.fandiaspraja.pokemon.core.data.source.local.room.UserDao
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao, private val pokemonDao: PokemonDao) {
//    user dao
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun loginUser(email: String, password: String): UserEntity? = userDao.loginUser(email, password)
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()
    fun getUserById(id: Int): Flow<UserEntity?> = userDao.getUserById(id)

    fun getAllPokemons(): PagingSource<Int, PokemonEntity> =
        pokemonDao.getAllPokemons()

    fun getAllLocalPokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllLocalPokemon()

    fun getPokemonByName(name: String): Flow<PokemonEntity?> = pokemonDao.getPokemonByName(name = name)


    suspend fun insertAllPokemon(data: List<PokemonEntity>) = pokemonDao.insertAllPokemon(data)
    suspend fun clearAllPokemon() = pokemonDao.clearAllPokemon()

    suspend fun getPokemonCount(): Int =
        pokemonDao.getPokemonCount()
}
