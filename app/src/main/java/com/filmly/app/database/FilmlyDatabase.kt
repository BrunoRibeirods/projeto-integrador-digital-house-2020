package com.filmly.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Film::class, Actor::class, Serie::class, Watched::class
    ], version = 4, exportSchema = false
)
abstract class FilmlyDatabase : RoomDatabase() {

    abstract val FilmlyDatabaseDao: FilmlyDao

    companion object {
        @Volatile
        private var INSTANCE: FilmlyDatabase? = null

        fun getInstance(context: Context): FilmlyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FilmlyDatabase::class.java,
                        "filmly_lists_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}