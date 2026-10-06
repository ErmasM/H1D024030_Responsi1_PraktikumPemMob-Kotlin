package com.ermasmuhammad.pokemon.data.api

import com.ermasmuhammad.pokemon.data.model.Pokemon
import com.ermasmuhammad.pokemon.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET("pokemon?limit=50")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): Pokemon
}