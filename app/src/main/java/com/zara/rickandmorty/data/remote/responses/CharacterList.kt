package com.zara.rickandmorty.data.remote.responses

data class CharacterList(
    val info: Info,
    val results: List<Character>
)