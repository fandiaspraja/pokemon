package com.fandiaspraja.pokemon.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int

    @Query("SELECT * FROM pokemon ORDER BY `index` ASC")
    fun getAllPokemons(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon")
    fun getAllLocalPokemon(): Flow<List<PokemonEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(data: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearAllPokemon()

    @Query("SELECT * FROM pokemon WHERE name = :name LIMIT 1")
    fun getPokemonByName(name: String): Flow<PokemonEntity?>
}