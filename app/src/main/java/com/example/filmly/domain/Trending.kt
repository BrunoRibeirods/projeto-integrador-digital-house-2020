package com.example.filmly.domain

import java.io.Serializable

class Trending(
    val titleMessage: String,
    val data: List<Card>
) : Serializable {
}