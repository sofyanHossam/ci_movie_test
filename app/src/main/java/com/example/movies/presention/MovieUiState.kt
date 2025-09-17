package com.example.movies.presention

import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.domain.model.Movie

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: List<MovieDto>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}