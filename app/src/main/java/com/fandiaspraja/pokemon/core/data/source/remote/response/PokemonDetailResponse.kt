package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
	@field:SerializedName("cries")
	val cries: Cries?,
	@field:SerializedName("location_area_encounters")
	val locationAreaEncounters: String?,
	@field:SerializedName("past_stats")
	val pastStats: List<PastStatsItem?>?,
	@field:SerializedName("types")
	val types: List<TypesItem?>?,
	@field:SerializedName("base_experience")
	val baseExperience: Int?,
	@field:SerializedName("held_items")
	val heldItems: List<Any?>?,
	@field:SerializedName("weight")
	val weight: Int?,
	@field:SerializedName("is_default")
	val isDefault: Boolean?,
	@field:SerializedName("past_types")
	val pastTypes: List<TypesItem?>?,
	@field:SerializedName("sprites")
	val sprites: Sprites?,
	@field:SerializedName("past_abilities")
	val pastAbilities: List<PastAbilitiesItem?>?,
	@field:SerializedName("abilities")
	val abilities: List<AbilitiesItem?>? ,
	@field:SerializedName("game_indices")
	val gameIndices: List<GameIndicesItem?>?,
	@field:SerializedName("species")
	val species: Species?,
	@field:SerializedName("stats")
	val stats: List<StatsItem?>?,
	@field:SerializedName("moves")
	val moves: List<MovesItem?>?,
	@field:SerializedName("name")
	val name: String?,
	@field:SerializedName("id")
	val id: Int?,
	@field:SerializedName("forms")
	val forms: List<FormsItem?>?,
	@field:SerializedName("height")
	val height: Int?,
	@field:SerializedName("order")
	val order: Int?
)
