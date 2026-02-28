package com.fandiaspraja.pokemon.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISessionRepository {
    suspend fun saveSession(id: Int?, name: String?, email: String?)
    suspend fun logout()
    fun getUserId(): Flow<Int?>
    fun isLogin(): Flow<Boolean>
}