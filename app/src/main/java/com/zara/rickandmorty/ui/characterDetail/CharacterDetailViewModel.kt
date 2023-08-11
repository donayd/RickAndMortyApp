package com.zara.rickandmorty.ui.characterDetail

import androidx.lifecycle.ViewModel
import com.zara.rickandmorty.data.remote.repository.CharacterRepository
import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    suspend fun getCharacterInfo(id: Int): Resource<Character> {
        return repository.getCharacterInfo(id)
    }

}