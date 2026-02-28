package com.fandiaspraja.pokemon.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val id: Int?,
    val name: String?,
    val localizedName: String?,
    val primaryAttr: String?,
    val attackType: String?,
    val roles: List<String>?,
    val img: String?,
    val icon: String?,
    val baseHealth: Int?,
    val baseAttackMin: Int?,
    val baseAttackMax: Int?,
    val baseMana: Int?,
    val moveSpeed: Int?,
    val isFavorite: Boolean = false
) : Parcelable