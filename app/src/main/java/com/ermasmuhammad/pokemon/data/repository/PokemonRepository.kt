package com.ermasmuhammad.pokemon.data.repository

import com.ermasmuhammad.pokemon.data.api.PokeApiService
import com.ermasmuhammad.pokemon.data.model.Pokemon

class PokemonRepository(
    private val api: PokeApiService
) {

    suspend fun getPokemonList(): List<Pokemon> {
        val response = api.getPokemonList()

        return response.results.map { item ->
            val id = item.url
                .trimEnd('/')
                .substringAfterLast('/')
                .toInt()

            getPokemonDetail(id)
        }
    }

    suspend fun getPokemonDetail(id: Int): Pokemon {
        return api.getPokemonDetail(id)
    }
}