package com.example.movies.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.movies.data.local.database.MovieDatabase
import com.example.movies.data.local.entity.MovieEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        // هنا بنستخدم ApplicationProvider علشان ناخد Context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // بنعمل Database In-Memory (يعني temporary مش بيتخزن على الجهاز)
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries() // بس مسموح في التست
            .build()

        movieDao = database.movieDao()
    }

    // بيتنفذ بعد كل test
    @After
    fun teardown() {
        database.close() // نقفل الداتابيز
    }

    @Test
    fun insertAndGetMovies_returnsInsertedMovie() = runBlocking {
        // Arrange
        val movie = MovieEntity(
            id = 1,
            title = "Tenet",
            overview = "Time inversion",
            posterPath = "/tenet.jpg",
            releaseDate = "2020-08-26",
            voteAverage = 8.0,
            adult = false,
            language = "en"
        )

        // Act
        movieDao.insertMovies(listOf(movie))
        val result = movieDao.getAllMovies()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Tenet", result.first().title)
    }
    @Test
    fun clearMovies_deletesAllMovies() = runBlocking {
        // Arrange
        val movie = MovieEntity(
            id = 2,
            title = "Inception",
            overview = "Dream within a dream",
            posterPath = "/inception.jpg",
            releaseDate = "2010-07-16",
            voteAverage = 9.0,
            adult = false,
            language = "en"
        )
        movieDao.insertMovies(listOf(movie))
        print(movieDao.getAllMovies().size)

        // Act
        movieDao.clearMovies()
        val result = movieDao.getAllMovies()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertMovie_replacesOnConflict() = runBlocking {
        // Arrange: فيلم أولاني
        val movie1 = MovieEntity(
            id = 3,
            title = "Interstellar",
            overview = "Space travel",
            posterPath = "/interstellar1.jpg",
            releaseDate = "2014-11-07",
            voteAverage = 8.6,
            adult = false,
            language = "en"
        )

        // فيلم تاني بنفس الـ id لكن بعنوان مختلف
        val movie2 = movie1.copy(
            title = "Interstellar Updated",
            posterPath = "/interstellar2.jpg"
        )

        // Act
        movieDao.insertMovies(listOf(movie1))
        movieDao.insertMovies(listOf(movie2)) // هيستبدل movie1

        val result = movieDao.getAllMovies()

        // Assert: المفروض يلاقي نسخة واحدة بس (المحدثة)
        assertEquals(1, result.size)
        assertEquals("Interstellar Updated", result.first().title)
        assertEquals("/interstellar2.jpg", result.first().posterPath)
    }

}