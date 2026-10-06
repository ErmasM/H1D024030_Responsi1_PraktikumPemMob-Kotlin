package com.ermasmuhammad.pokemon.data.model

data class Stat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)