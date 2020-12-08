package com.example.filmly.data.model

data class Serie(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?
) : Card