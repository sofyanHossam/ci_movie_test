package com.example.movies.presention.viewmodel

import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.data.remote.dto.MovieResponse
import com.example.movies.domain.repo.MovieRepository
import com.example.movies.domain.usecase.GetPopularMoviesUseCase
import com.example.movies.presention.MovieUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: MovieViewModel

    private val repository: MovieRepository = mockk()
    private val useCase = GetPopularMoviesUseCase(repository)


    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies returns success state`() = testScope.runTest {
        // Arrange
        val movies = listOf(
            MovieDto(1, "Tenet", "Overview", null, "2020-08-26", 8.5, false, "en")
        )
        val response = MovieResponse(
            page = 1,
            results = movies,
            total_pages = 1,
            total_results = movies.size
        )
        coEvery { getPopularMoviesUseCase() } returns response

        // Act
        viewModel = MovieViewModel(getPopularMoviesUseCase)
        testDispatcher.scheduler.advanceUntilIdle() // Let coroutines execute

        // Assert
        val state = viewModel.uiState.first()
        assertTrue(state is MovieUiState.Success)
        assertEquals(movies, (state as MovieUiState.Success).movies)
    }

    @Test
    fun `loadMovies returns error state`() = testScope.runTest {
        // Arrange
        coEvery { getPopularMoviesUseCase() } throws Exception("Network failed")

        // Act
        viewModel = MovieViewModel(getPopularMoviesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.first()
        assertTrue(state is MovieUiState.Error)
        assertEquals("Network failed", (state as MovieUiState.Error).message)
    }

    @Test
    fun `invoke returns movies from repository`() = runTest {
        // Arrange
        val movies = listOf(
            MovieDto(1, "Inception", "Overview", null, "2010-07-16", 8.8, false, "en")
        )
        val response = MovieResponse(
            page = 1,
            results = movies,
            total_pages = 1,
            total_results = movies.size
        )

        coEvery { repository.getPopularMovies(1) } returns response

        // Act
        val result = useCase(1)

        // Assert
        assertEquals(response, result)
        coVerify { repository.getPopularMovies(1) }
    }
}
