package com.angelo.pokedex.pokedexapi

import com.angelo.pokedex.model.PokemonResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokedexapiService {

    @GET("pokemon")
    fun obtenerListaPokemon(@Query("offset")offset:Int,@Query("limit")limit:Int):Call<PokemonResult>

}