package com.filmly.app.data.model

import java.io.Serializable

interface Card: Serializable {
    val id: Int?
    val name: String?
    val image: String?
    val descricao: String?
    val type: String?
}