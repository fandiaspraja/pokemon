package com.fandiaspraja.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VersionGroupDetailsItem(
	@field:SerializedName("level_learned_at")
	val levelLearnedAt: Int? = null,

	@field:SerializedName("version_group")
	val versionGroup: VersionGroup? = null,

	@field:SerializedName("move_learn_method")
	val moveLearnMethod: MoveLearnMethod? = null,

	@field:SerializedName("order")
	val order: Any? = null
)
