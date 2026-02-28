package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Other(
	@field:SerializedName("dream_world")
	val dreamWorld: DreamWorld? = null,

	@field:SerializedName("showdown")
	val showdown: Showdown? = null,

	@field:SerializedName("official-artwork")
	val officialArtwork: OfficialArtwork? = null,

	@field:SerializedName("home")
	val home: Home? = null
)
