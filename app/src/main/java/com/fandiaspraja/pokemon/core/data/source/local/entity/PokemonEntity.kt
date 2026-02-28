package com.fandiaspraja.pokemon.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val url: String,
    val index: Int
)
