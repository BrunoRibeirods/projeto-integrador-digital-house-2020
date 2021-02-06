package com.filmly.app.data.model

import java.io.Serializable

class HeadLists(
    val titleMessage: String,
    val data: List<Card>,
    val cardInfo: Int
) : Serializable {
}