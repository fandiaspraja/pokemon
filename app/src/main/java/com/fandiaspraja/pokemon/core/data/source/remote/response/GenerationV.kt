package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenerationV(
	@field:SerializedName("black-white")
	val blackWhite: BlackWhite? = null)
