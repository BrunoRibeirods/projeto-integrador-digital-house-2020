package com.example.filmly.domain

import java.io.Serializable

data class CardDetail(
    val title: String,
    val cardImage: Int,
    val sinopse: String
) : Serializable {
}