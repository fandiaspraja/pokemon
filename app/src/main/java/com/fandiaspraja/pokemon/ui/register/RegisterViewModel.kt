package com.fandiaspraja.pokemon.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.User
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Boolean>>(Resource.Idle())
    val registerState: StateFlow<Resource<Boolean>> = _registerState.asStateFlow()

    fun register(user: User) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            try {
                pokemonUseCase.insertUser(user)
                _registerState.value = Resource.Success(true)
            } catch (e: Exception) {
                _registerState.value = Resource.Error(e.message ?: "Gagal register")
            }
        }
    }
}