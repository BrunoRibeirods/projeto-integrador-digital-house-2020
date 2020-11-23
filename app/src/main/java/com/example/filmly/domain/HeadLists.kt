package com.example.filmly.domain

import java.io.Serializable

class HeadLists(
    val titleMessage: String,
    val data: List<Card>
) : Serializable {
}