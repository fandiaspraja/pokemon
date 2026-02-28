package com.fandiaspraja.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _detail = MutableStateFlow<Resource<PokemonDetail>>(Resource.Loading())
    val detail: StateFlow<Resource<PokemonDetail>> = _detail

    fun getDetail(name: String) {
        viewModelScope.launch {
            pokemonUseCase.getPokemonDetail(name)
                .catch { e ->
                    _detail.value = Resource.Error(e.message ?: "Terjadi error tidak terduga")
                }
                .collect { resource ->
                    _detail.value = resource
                }
        }
    }
}