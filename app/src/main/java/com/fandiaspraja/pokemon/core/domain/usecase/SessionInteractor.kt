package com.fandiaspraja.pokemon.core.domain.usecase

import com.fandiaspraja.pokemon.core.domain.repository.ISessionRepository
import kotlinx.coroutines.flow.Flow

class SessionInteractor(
    private val repo: ISessionRepository
) : SessionUseCase {

    override suspend fun saveSession(id: Int?, name: String?, email: String?) =
        repo.saveSession(id, name, email)

    override suspend fun logout() = repo.logout()

    override fun getUserId(): Flow<Int?> = repo.getUserId()

    override fun isLogin(): Flow<Boolean> = repo.isLogin()
}