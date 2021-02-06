package com.filmly.app.data.model

import java.io.Serializable

data class CardDetail(
    val cardInfo: Int,
    val card: Card
) : Serializable {

    companion object {
        val FILM = 1
        val ACTOR = 2
        val SERIE = 3
        val TRENDING = 4
    }
}