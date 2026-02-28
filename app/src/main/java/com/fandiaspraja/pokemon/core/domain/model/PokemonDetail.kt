package com.fandiaspraja.pokemon.core.domain.model

import com.fandiaspraja.pokemon.core.data.source.remote.response.AbilitiesItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.Ability
import com.fandiaspraja.pokemon.core.data.source.remote.response.Cries
import com.fandiaspraja.pokemon.core.data.source.remote.response.FormsItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.GameIndicesItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.MovesItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.PastAbilitiesItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.PastStatsItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.Species
import com.fandiaspraja.pokemon.core.data.source.remote.response.Sprites
import com.fandiaspraja.pokemon.core.data.source.remote.response.StatsItem
import com.fandiaspraja.pokemon.core.data.source.remote.response.Type
import com.fandiaspraja.pokemon.core.data.source.remote.response.TypesItem

data class PokemonDetail(
    val cries: Cries? = null,
    val locationAreaEncounters: String? = null,
    val pastStats: List<PastStatsItem?>? = null,
    val types: List<TypesItem?>? = null,
    val baseExperience: Int? = null,
    val heldItems: List<Any?>? = null,
    val weight: Int? = null,
    val isDefault: Boolean? = null,
    val pastTypes: List<TypesItem?>? = null,
    val sprites: Sprites? = null,
    val pastAbilities: List<PastAbilitiesItem?>? = null,
    val abilities: List<AbilitiesItem?>? = null,
    val gameIndices: List<GameIndicesItem?>? = null,
    val species: Species? = null,
    val stats: List<StatsItem?>? = null,
    val moves: List<MovesItem?>? = null,
    val name: String? = null,
    val id: Int? = null,
    val forms: List<FormsItem?>? = null,
    val height: Int? = null,
    val order: Int? = null
)
