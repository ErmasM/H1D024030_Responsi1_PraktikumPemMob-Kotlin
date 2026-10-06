package com.ermasmuhammad.pokemon.ui.state

import com.ermasmuhammad.pokemon.data.model.Pokemon

sealed interface PokemonUiState {

    data object Loading : PokemonUiState

    data class Success(
        val pokemon: List<Pokemon>
    ) : PokemonUiState

    data class Error(
        val message: String
    ) : PokemonUiState
}