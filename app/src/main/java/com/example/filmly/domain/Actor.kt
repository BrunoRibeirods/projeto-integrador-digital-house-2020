package com.example.filmly.domain

data class Actor(
    override val id: Int,
    override val name: String,
    override val image: Int
) : Card