package com.ermasmuhammad.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermasmuhammad.pokemon.data.api.RetrofitClient
import com.ermasmuhammad.pokemon.data.repository.PokemonRepository
import com.ermasmuhammad.pokemon.ui.state.PokemonDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository =
        PokemonRepository(RetrofitClient.api)

    private val _detailState =
        MutableStateFlow<PokemonDetailUiState>(
            PokemonDetailUiState.Loading
        )

    val detailState: StateFlow<PokemonDetailUiState> =
        _detailState.asStateFlow()

    fun getPokemonDetail(id: Int) {

        viewModelScope.launch {

            _detailState.value =
                PokemonDetailUiState.Loading

            try {

                val pokemon =
                    repository.getPokemonDetail(id)

                _detailState.value =
                    PokemonDetailUiState.Success(
                        pokemon = pokemon
                    )

            } catch (e: Exception) {

                _detailState.value =
                    PokemonDetailUiState.Error(
                        message =
                            e.message ?: "Terjadi kesalahan"
                    )
            }
        }
    }
}