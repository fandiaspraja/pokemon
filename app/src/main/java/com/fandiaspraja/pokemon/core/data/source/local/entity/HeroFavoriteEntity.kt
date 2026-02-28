package com.fandiaspraja.pokemon.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hero_favorites")
data class HeroFavoriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val localizedName: String,
    val img: String,
    val roles: String,
    val attackType: String,
    val primaryAttr: String,
    val baseHealth: Int,
    val baseAttackMin: Int,
    val baseAttackMax: Int,
    val baseMana: Int,
    val moveSpeed: Int,
    val isFavorite: Boolean = true
)