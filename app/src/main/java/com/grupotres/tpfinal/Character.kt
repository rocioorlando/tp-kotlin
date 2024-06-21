package com.grupotres.tpfinal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class CharacterResponse(
    val results: List<Character>
)

data class Character(
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String
)

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