package com.example.filmly.data.model

import com.example.filmly.database.Watched

interface Watchable {
    fun asWatched(): Watched
}