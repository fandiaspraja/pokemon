package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameIndicesItem(
	@field:SerializedName("game_index")
	val gameIndex: Int? = null,

	@field:SerializedName("version")
	val version: Version? = null
)
