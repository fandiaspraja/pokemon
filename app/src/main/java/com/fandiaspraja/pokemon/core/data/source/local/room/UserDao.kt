package com.fandiaspraja.pokemon.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fandiaspraja.pokemon.core.data.source.local.entity.HeroFavoriteEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.PokemonEntity
import com.fandiaspraja.pokemon.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): UserEntity


    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>


    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): Flow<UserEntity>

}