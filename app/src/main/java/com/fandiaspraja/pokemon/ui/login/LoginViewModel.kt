package com.fandiaspraja.pokemon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.User
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import com.fandiaspraja.pokemon.core.domain.usecase.SessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(private val pokemonUseCase: PokemonUseCase,     private val sessionUseCase: SessionUseCase
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Resource<User?>>(Resource.Idle())
    val loginResult: StateFlow<Resource<User?>> = _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading()

            try {
                val user = pokemonUseCase.loginUser(email, password)
                if (user != null) {
                    sessionUseCase.saveSession(user.id, user.name, user.email)
                    _loginResult.value = Resource.Success(user)
                } else {
                    _loginResult.value = Resource.Error("User tidak ditemukan")
                }
            } catch (e: Exception) {
                _loginResult.value = Resource.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}