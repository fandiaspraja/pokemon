package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovesItem(
	@field:SerializedName("version_group_details")
	val versionGroupDetails: List<VersionGroupDetailsItem?>? = null,

	@field:SerializedName("move")
	val move: Move? = null
)
