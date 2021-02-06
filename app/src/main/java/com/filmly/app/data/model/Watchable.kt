package com.filmly.app.data.model

import com.filmly.app.database.Watched

interface Watchable {
    fun asWatched(): Watched
}