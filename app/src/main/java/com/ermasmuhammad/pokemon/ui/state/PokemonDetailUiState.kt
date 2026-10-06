package com.ermasmuhammad.pokemon.ui.state

import com.ermasmuhammad.pokemon.data.model.Pokemon

sealed interface PokemonDetailUiState {

    data object Loading : PokemonDetailUiState

    data class Success(
        val pokemon: Pokemon
    ) : PokemonDetailUiState

    data class Error(
        val message: String
    ) : PokemonDetailUiState
}