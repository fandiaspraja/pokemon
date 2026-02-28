package com.fandiaspraja.pokemon.core.utils

import android.util.Log
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.UserEntity
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonDetailResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonResponse
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.model.User

object DataMapper {

    fun mapListEntityToDomainUsers(input: List<UserEntity>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                id = it.id,
                email = it.email,
                password = it.password
            )
        }

        return userList
    }

    fun mapEntityToDomainUser(input: UserEntity): User? {
        val user = User(
            id = input.id,
            name = input.name,
            email = input.email,
            password = input.password
        )

        return user
    }

    fun mapDomainToEntityUser(input: User): UserEntity {
        val user = input.id?.let {
            UserEntity(
                id = it,
                name = input.name,
                email = input.email,
                password = input.password
            )
        }

        return user!!
    }

    fun mapListDomainToEntityUsers(input: List<User>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                name = it.name,
                email = it.email,
                password = it.password
            )
        }

        return userList
    }

    fun mapListResponseToDomainPokemons(input: List<PokemonResponse>): List<Pokemon> {
        val pokemonList = ArrayList<Pokemon>()
        input.map {
            val pokemon = Pokemon(
                id = extractIdFromUrl("${it.url}"),
                name = it.name,
                url = it.url
            )
        }

        return pokemonList
    }

    fun mapListResponseToEntityPokemons(input: List<PokemonResponse>, offset: Int): List<PokemonEntity> {
        return input.mapIndexed { i, data ->
            PokemonEntity(
                id = extractIdFromUrl("${data.url}"),
                name = "${data.name}",
                url = "${data.url}",
                index = offset + i
            )
        }
    }

    fun mapListEntityToDomainPokemons(input: List<PokemonEntity>): List<Pokemon> {
        val pokemonList = ArrayList<Pokemon>()
        input.map {
            val pokemon = Pokemon(
                id = it.id,
                name = it.name,
                url = it.url
            )

            pokemonList.add(pokemon)
        }

        return pokemonList
    }

    fun mapEntityToDomainPokemon(input: PokemonEntity): Pokemon {
        val pokemon = Pokemon(
            input.id,
            input.name,
            input.url
        )

        return pokemon
    }

    fun mapResponseToDomainPokemonDetail(input: PokemonDetailResponse): PokemonDetail {
        val pokemon = PokemonDetail(
            cries = input.cries,
            locationAreaEncounters = input.locationAreaEncounters,
            pastStats = input.pastStats,
            types = input.types,
            baseExperience = input.baseExperience,
            heldItems = input.heldItems,
            weight = input.weight,
            isDefault = input.isDefault,
            pastTypes = input.pastTypes,
            sprites = input.sprites,
            pastAbilities = input.pastAbilities,
            abilities = input.abilities,
            gameIndices = input.gameIndices,
            species = input.species,
            stats = input.stats,
            moves = input.moves,
            name = input.name,
            id = input.id,
            forms = input.forms,
            height = input.height,
            order = input.order
        )

        return pokemon
    }

    fun extractIdFromUrl(url: String): Int {
        return url.trimEnd('/')
            .split("/")
            .last()
            .toInt()
    }




}