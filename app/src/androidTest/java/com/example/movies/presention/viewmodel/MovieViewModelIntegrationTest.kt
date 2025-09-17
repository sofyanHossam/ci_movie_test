package com.example.movies.presention.viewmodel

import com.example.movies.domain.usecase.GetPopularMoviesUseCase
import com.example.movies.presention.MovieUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MovieViewModelIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = MovieViewModel(getPopularMoviesUseCase)
    }

    @Test
    fun loadMovies_returnsFakeData() = runTest {
        val state = viewModel.uiState.first()
        Assert.assertTrue(state is MovieUiState.Success)
        Assert.assertEquals("Fake Tenet", (state as MovieUiState.Success).movies.first().title)
    }
}