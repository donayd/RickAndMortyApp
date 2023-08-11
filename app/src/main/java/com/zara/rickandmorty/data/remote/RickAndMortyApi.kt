package com.zara.rickandmorty.data.remote

import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.data.remote.responses.CharacterList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacterList(
        @Query("page") page: Int
    ): CharacterList

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Character

}