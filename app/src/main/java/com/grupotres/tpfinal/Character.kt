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
        @Query("name") name: String?,        // Parámetro de consulta para el nombre del personaje
        @Query("status") status: String?,    // Parámetro de consulta para el estado del personaje
        @Query("species") species: String?,  // Parámetro de consulta para la especie del personaje
        @Query("type") type: String?,        // Parámetro de consulta para el tipo del personaje
        @Query("gender") gender: String?     // Parámetro de consulta para el género del personaje
    ): Call<CharacterResponse>              // Llamada que devuelve una respuesta con una lista de personajes
}
