package com.example.movies.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

