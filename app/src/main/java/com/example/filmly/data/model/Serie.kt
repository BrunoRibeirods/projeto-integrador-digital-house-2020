package com.example.filmly.data.model

import com.example.filmly.database.Serie
import com.example.filmly.database.Watched

data class Serie(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?
) : Card, Watchable {
    fun asSerieDatabase(): Serie {
        return Serie(marvel_id = id, name = name, image = image, description = descricao)
    }

    override fun asWatched(): Watched {
        return Watched(marvel_id = id, name = name, image = image, description = descricao)
    }
}