package com.example.filmly.data.model

import com.example.filmly.database.Film
import com.example.filmly.database.Watched

data class Film(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?
) : Card, Watchable {
    fun asFilmDatabase(): Film {
        return Film(id = id, name = name, image = image, description = descricao)
    }

    override fun asWatched(): Watched {
        return Watched(id = id, name = name, image = image, description = descricao)
    }
}