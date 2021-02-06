package com.filmly.app.data.model

import com.filmly.app.database.Film
import com.filmly.app.database.Watched

data class Film(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?,
    override val type: String?
) : Card, Watchable {
    fun asFilmDatabase(): Film {
        return Film(name = name, image = image, description = descricao, marvel_id = id)
    }

    override fun asWatched(): Watched {
        return Watched( name = name, image = image, description = descricao, marvel_id = id)
    }
}