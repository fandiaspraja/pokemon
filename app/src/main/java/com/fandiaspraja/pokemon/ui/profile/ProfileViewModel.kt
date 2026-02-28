package com.fandiaspraja.pokemon.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fandiaspraja.pokemon.core.domain.model.User
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import com.fandiaspraja.pokemon.core.domain.usecase.SessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val pokemonUseCase: PokemonUseCase,
    private val sessionUseCase: SessionUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            sessionUseCase.getUserId().collect { id ->
                if (id != null) {
                    pokemonUseCase.getUserById(id).collect {
                        _user.value = it
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionUseCase.logout()
        }
    }
}