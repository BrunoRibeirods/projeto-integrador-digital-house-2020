package com.example.filmly.data.model

data class Actor(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?
) : Card