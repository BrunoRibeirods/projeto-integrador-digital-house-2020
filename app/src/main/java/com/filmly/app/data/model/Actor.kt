package com.filmly.app.data.model

import com.filmly.app.database.Actor
import com.filmly.app.ui.search.KnownFor

data class Actor(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?,
    override val type: String?,
    val known_for: List<KnownFor>? = null,
    override val popularity: Double?
) : Card {
    fun asActorDatabase(): Actor {
        return Actor(marvel_id = id, name = name, image = image, description = "")
    }
}