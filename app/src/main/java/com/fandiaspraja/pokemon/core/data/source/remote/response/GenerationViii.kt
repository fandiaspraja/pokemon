package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenerationViii(
	@field:SerializedName("brilliant-diamond-shining-pearl")
	val brilliantDiamondShiningPearl: BrilliantDiamondShiningPearl? = null,

	@field:SerializedName("icons")
	val icons: Icons? = null
)
