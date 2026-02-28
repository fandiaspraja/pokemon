package com.fandiaspraja.pokemon.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String? = null,
    val url: String? = null
): Parcelable
