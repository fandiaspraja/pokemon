package com.fandiaspraja.pokemon.core.domain.usecase

import androidx.paging.PagingData
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.model.User
import com.fandiaspraja.pokemon.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonInteractor(
    private val pokemonRepository: IPokemonRepository
): PokemonUseCase {

    override suspend fun refresh() = pokemonRepository.refresh()

    override fun getPokemons(): Flow<PagingData<Pokemon>> = pokemonRepository.getPokemons()

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail>> = pokemonRepository.getPokemonDetail(name)


    override suspend fun insertUser(user: User) = pokemonRepository.insertUser(user)


    override fun getAllUsers(): Flow<List<User>> = pokemonRepository.getAllUsers()


    override fun getUserById(id: Int): Flow<User?> = pokemonRepository.getUserById(id)


    override suspend fun loginUser(
        email: String,
        password: String
    ): User? = pokemonRepository.loginUser(email, password)


    override fun getAllPokemons(): Flow<List<Pokemon>> = pokemonRepository.getAllPokemons()

    override fun getPokemonByName(name: String): Flow<Pokemon?> = pokemonRepository.getPokemonByName(name = name)

    override suspend fun insertAllPokemon(data: List<Pokemon>) = pokemonRepository.insertAllPokemon(data)

    override suspend fun clearAllPokemon() = pokemonRepository.clearAllPokemon()

}