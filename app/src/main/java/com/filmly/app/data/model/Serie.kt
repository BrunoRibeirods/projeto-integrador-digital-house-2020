package com.filmly.app.data.model

import com.filmly.app.database.Serie
import com.filmly.app.database.Watched

data class Serie(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?,
    override val type: String?, override val popularity: Double?
) : Card, Watchable {
    fun asSerieDatabase(): Serie {
        return Serie(marvel_id = id, name = name, image = image, description = descricao)
    }

    override fun asWatched(): Watched {
        return Watched(marvel_id = id, name = name, image = image, description = descricao)
    }
}