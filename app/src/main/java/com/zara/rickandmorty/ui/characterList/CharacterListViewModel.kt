package com.zara.rickandmorty.ui.characterList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zara.rickandmorty.data.models.CharacterItem
import com.zara.rickandmorty.data.remote.repository.CharacterRepository
import com.zara.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private var currentPage = 1

    var characterList = mutableStateOf<List<CharacterItem>>(listOf())
    private var cachedCharacterList = listOf<CharacterItem>()
    private var isSearchStart = true

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    init {
        loadCharacterPaginated()
    }

    fun loadCharacterPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCharacterList(currentPage)
            if (result is Resource.Success) {
                endReached.value = result.data?.info?.next == null
                val characterItems = result.data!!.results.mapIndexed { _, character ->
                    CharacterItem(
                        id = character.id, name = character.name, imageUrl = character.image
                    )
                }
                currentPage++
                loadError.value = ""
                isLoading.value = false
                characterList.value += characterItems
            } else if (result is Resource.Error) {
                loadError.value = result.message!!
                isLoading.value = false
            }
        }
    }

    fun searchCharacter(query: String) {
        val listToSearch = if (isSearchStart) {
            characterList.value
        } else {
            cachedCharacterList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                characterList.value = cachedCharacterList
                isSearching.value = false
                isSearchStart = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.id.toString() == query.trim()
            }
            if (isSearchStart) {
                cachedCharacterList = characterList.value
                isSearchStart = false
            }
            characterList.value = results
            isSearching.value = true
        }
    }
}