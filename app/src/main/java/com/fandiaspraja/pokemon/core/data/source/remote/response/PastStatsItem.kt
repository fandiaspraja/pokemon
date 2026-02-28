package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PastStatsItem(
	@field:SerializedName("generation")
	val generation: Generation? = null,

	@field:SerializedName("stats")
	val stats: List<StatsItem?>? = null
)
