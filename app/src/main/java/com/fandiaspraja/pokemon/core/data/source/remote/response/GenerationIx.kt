package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenerationIx(
	@field:SerializedName("scarlet-violet")
	val scarletViolet: ScarletViolet? = null)
