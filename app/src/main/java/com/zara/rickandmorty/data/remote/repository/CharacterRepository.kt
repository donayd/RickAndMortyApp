package com.zara.rickandmorty.data.remote.repository

import com.zara.rickandmorty.data.remote.RickAndMortyApi
import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.data.remote.responses.CharacterList
import com.zara.rickandmorty.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {

    suspend fun getCharacterList(page: Int): Resource<CharacterList> {
        val response = try {
            api.getCharacterList(page)
        } catch (e: Exception) {
            return Resource.Error("An error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getCharacterInfo(id: Int): Resource<Character> {
        val response = try {
            api.getCharacter(id)
        } catch (e: Exception) {
            return Resource.Error("An error occurred")
        }
        return Resource.Success(response)
    }

}