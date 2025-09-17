package com.example.movies.domain.usecase


import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.data.remote.dto.MovieResponse
import com.example.movies.domain.repo.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetPopularMoviesUseCaseTest {

    // mock repository
    private val repository: MovieRepository = mockk()
    // system under test
    private val useCase = GetPopularMoviesUseCase(repository)

    @Test
    fun `invoke returns data from repository`() = runTest {
        // arrange
        val movies = listOf(
            MovieDto(
                id = 1,
                title = "Tenet",
                overview = "Time-bending thriller",
                poster_path = "/tenet.jpg",
                release_date = "2020-08-26",
                vote_average = 8.5,
                adult = false,
                original_language = "en"
            )
        )
        val response = MovieResponse(
            page = 1,
            results = movies,
            total_pages = 1,
            total_results = movies.size
        )

        coEvery { repository.getPopularMovies(1) } returns response

        // act
        val result = useCase(1)

        // assert
        assertEquals(response, result)
    }

    @Test
    fun `invoke throws when repository fails`() = runTest {
        // arrange
        coEvery { repository.getPopularMovies(1) } throws RuntimeException("Network fail")

        // act & assert
        val thrown = assertFailsWith<RuntimeException> {
            useCase(1)
        }
        assertEquals("Network fail", thrown.message)
    }

    @Test
    fun `invoke uses default page parameter`() = runTest {
        // arrange
        val movies = listOf(
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
            results = movies,
            total_pages = 1,
            total_results = movies.size
        )

        // repository expected to be called with page = 1 by default
        coEvery { repository.getPopularMovies(1) } returns response

        // act
        val result = useCase() // call with default param

        // assert
        assertEquals(response, result)
    }
}
