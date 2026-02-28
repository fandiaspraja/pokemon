package com.fandiaspraja.pokemon.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun pokemonDao(): PokemonDao
}

