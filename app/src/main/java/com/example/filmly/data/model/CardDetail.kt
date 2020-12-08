package com.example.filmly.data.model

import java.io.Serializable

data class CardDetail(
    val title: String?,
    val cardImage: String?,
    val sinopse: String?
) : Serializable {
}