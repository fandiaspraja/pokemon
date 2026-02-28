package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
	@field:SerializedName("name")
	val name: String?,
	@field:SerializedName("url")
	val url: String?
)
