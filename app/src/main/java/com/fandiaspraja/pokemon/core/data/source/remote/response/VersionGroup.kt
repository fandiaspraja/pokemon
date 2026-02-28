package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VersionGroup(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
