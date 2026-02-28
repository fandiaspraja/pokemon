package com.fandiaspraja.pokemon.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonRemoteKeys

@Dao
interface PokemonRemoteKeysDao {

    @Query("SELECT * FROM pokemon_remote_keys WHERE pokemonId = :id")
    suspend fun remoteKeys(id: Int): PokemonRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PokemonRemoteKeys>)

    @Query("DELETE FROM pokemon_remote_keys")
    suspend fun clearRemoteKeys()
}