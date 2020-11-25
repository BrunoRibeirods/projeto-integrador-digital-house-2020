package com.example.filmly.domain

data class Serie(
    override val id: Int,
    override val name: String,
    override val image: Int,
    override val descricao: String
) : Card