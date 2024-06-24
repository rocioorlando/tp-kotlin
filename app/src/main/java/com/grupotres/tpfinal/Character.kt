package com.grupotres.tpfinal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Data class para la respuesta de la API, contiene una lista de personajes
data class CharacterResponse(
    val results: List<Character>
)

// Data class para representar un personaje con sus propiedades
data class Character(
    val name: String,   // Nombre del personaje
    val status: String, // Estado del personaje (vivo, muerto, desconocido)
    val species: String,// Especie del personaje (humano, alien, etc.)
    val type: String,   // Tipo del personaje
    val gender: String, // Género del personaje
    val image: String   // URL de la imagen del personaje
)

// Interface que define los endpoints de la API de Rick and Morty
interface RickAndMortyApiService {
    @GET("character")
    fun getCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): Call<CharacterResponse>
}
