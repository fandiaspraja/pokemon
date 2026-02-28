package com.fandiaspraja.pokemon.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fandiaspraja.pokemon.core.data.source.local.entity.HeroFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM hero_favorites")
    fun getAllFavorites(): Flow<List<HeroFavoriteEntity>>

    @Query("SELECT * FROM hero_favorites WHERE id = :id LIMIT 1")
    fun getFavoriteById(id: Int): Flow<HeroFavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(hero: HeroFavoriteEntity)


    @Delete
    suspend fun deleteFavorite(hero: HeroFavoriteEntity)

    @Query("DELETE FROM hero_favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}
