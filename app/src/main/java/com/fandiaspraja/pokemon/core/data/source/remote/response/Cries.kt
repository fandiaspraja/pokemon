package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Cries(
	@field:SerializedName("legacy")
	val legacy: String? = null,
	@field:SerializedName("latest")
	val latest: String? = null
)
