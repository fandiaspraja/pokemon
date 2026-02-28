package com.fandiaspraja.pokemon.core.data.source.repository

import com.fandiaspraja.pokemon.core.data.source.local.datastore.SessionDataStore
import com.fandiaspraja.pokemon.core.domain.repository.ISessionRepository
import kotlinx.coroutines.flow.Flow

class SessionRepository(
    private val sessionDataStore: SessionDataStore
) : ISessionRepository {

    override suspend fun saveSession(id: Int?, name: String?, email: String?) {
        sessionDataStore.saveSession(id, name, email)
    }

    override suspend fun logout() {
        sessionDataStore.clearSession()
    }

    override fun getUserId(): Flow<Int?> = sessionDataStore.userId

    override fun isLogin(): Flow<Boolean> = sessionDataStore.isLogin
}