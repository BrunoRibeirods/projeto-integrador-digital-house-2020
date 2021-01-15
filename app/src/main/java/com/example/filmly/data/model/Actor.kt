package com.example.filmly.data.model

import com.example.filmly.database.Actor

data class Actor(
    override val id: Int?,
    override val name: String?,
    override val image: String?,
    override val descricao: String?,
    override val type: String?
) : Card {
    fun asActorDatabase(): Actor {
        return Actor(marvel_id = id, name = name, image = image, description = descricao)
    }
}