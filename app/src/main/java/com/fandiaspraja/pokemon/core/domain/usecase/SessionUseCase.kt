package com.fandiaspraja.pokemon.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface SessionUseCase {
    suspend fun saveSession(id: Int?, name: String?, email: String?)
    suspend fun logout()
    fun getUserId(): Flow<Int?>
    fun isLogin(): Flow<Boolean>
}