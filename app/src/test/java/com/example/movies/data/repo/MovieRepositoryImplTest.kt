package com.example.movies.data.repo


import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.remote.MovieApi
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.data.remote.dto.MovieResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    // Mocks for dependencies
    private val api: MovieApi = mockk()
    private val movieDao: MovieDao = mockk()

    // System under test
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(api = api, movieDao = movieDao)
    }

    @Test
    fun `getPopularMovies returns cached movies when available`() = runTest {
        // Arrange: prepare cached entities returned by DAO
        val cached = listOf(
            MovieEntity(
                id = 1,
                title = "Tenet",
                overview = "Time-bending thriller",
                posterPath = "/tenet.jpg",
                releaseDate = "2020-08-26",
                voteAverage = 8.5,
                adult = false,
                language = "en"
            )
        )

        // Stub DAO to return cached list
        coEvery { movieDao.getAllMovies() } returns cached

        // Act
        val result = repository.getPopularMovies(page = 1)

        // Assert: should return results from cache and NOT call API
        assertEquals(1, result.results.size)
        assertEquals("Tenet", result.results.first().title)
        // verify interactions
        coVerify(exactly = 1) { movieDao.getAllMovies() }
        coVerify(exactly = 0) { api.getPopularMovies(any(), any(), any()) }
    }

    @Test
    fun `getPopularMovies fetches from API when cache is empty and saves to DB`() = runTest {
        // Arrange: DAO empty
        coEvery { movieDao.getAllMovies() } returns emptyList()

        // Prepare API response
        val moviesFromApi = listOf(
            MovieDto(
                id = 2,
                title = "Inception",
                overview = "Dream heist",
                poster_path = "/inception.jpg",
                release_date = "2010-07-16",
                vote_average = 9.0,
                adult = false,
                original_language = "en"
            )
        )
        val response = MovieResponse(
            page = 1,
            results = moviesFromApi,
            total_pages = 1,
            total_results = moviesFromApi.size
        )

        // Stub API and DAO write operations
        coEvery { api.getPopularMovies(any(), any(), any()) } returns response
        coEvery { movieDao.clearMovies() } returns Unit // or use just Runs
        coEvery { movieDao.insertMovies(any()) } returns Unit

        // Act
        val result = repository.getPopularMovies(page = 1)

        // Assert
        assertEquals(1, result.results.size)
        assertEquals("Inception", result.results.first().title)

        // verify interactions: API called, DAO cleared and inserted
        coVerify(exactly = 1) { movieDao.getAllMovies() }
        coVerify(exactly = 1) { api.getPopularMovies(any(), 1, "en-US") }
        coVerify(exactly = 1) { movieDao.clearMovies() }
        coVerify(exactly = 1) { movieDao.insertMovies(any()) }
    }

    @Test
    fun `getPopularMovies throws when API fails`() = runTest {
        // Arrange: empty cache
        coEvery { movieDao.getAllMovies() } returns emptyList()

        // API throws
        coEvery { api.getPopularMovies(any(), any(), any()) } throws RuntimeException("Network fail")

        // Act & Assert: repository should propagate exception (or you can assert specific behavior)
        val thrown = assertFailsWith<RuntimeException> {
            repository.getPopularMovies(page = 1)
        }
        assertEquals("Network fail", thrown.message)
        // make sure we did not call insertMovies if API failed
        coVerify(exactly = 1) { movieDao.getAllMovies() }
        coVerify(exactly = 1) { api.getPopularMovies(any(), 1, "en-US") }
        coVerify(exactly = 0) { movieDao.insertMovies(any()) }
    }
}
