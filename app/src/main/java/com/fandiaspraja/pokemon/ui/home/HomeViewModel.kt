package com.fandiaspraja.pokemon.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _pokemonList = MutableStateFlow<Resource<List<Pokemon>>>(Resource.Loading())
    val pokemonList: StateFlow<Resource<List<Pokemon>>> = _pokemonList


    val pokemons = pokemonUseCase.getPokemons()
        .cachedIn(viewModelScope)
}