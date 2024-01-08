package com.zara.rickandmorty.data.remote.responses

data class Info(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: Any
)