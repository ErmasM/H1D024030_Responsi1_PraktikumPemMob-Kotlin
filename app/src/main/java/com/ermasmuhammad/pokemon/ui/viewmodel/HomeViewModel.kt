package com.ermasmuhammad.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermasmuhammad.pokemon.data.api.RetrofitClient
import com.ermasmuhammad.pokemon.data.repository.PokemonRepository
import com.ermasmuhammad.pokemon.ui.state.PokemonUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = PokemonRepository(RetrofitClient.api)

    private val _uiState =
        MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)

    val uiState: StateFlow<PokemonUiState> =
        _uiState.asStateFlow()

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonList()

                _uiState.value = PokemonUiState.Success(
                    pokemon = response
                )
            } catch (e: Exception) {
                _uiState.value = PokemonUiState.Error(
                    message = e.message ?: "Terjadi kesalahan"
                )
            }
        }
    }
}