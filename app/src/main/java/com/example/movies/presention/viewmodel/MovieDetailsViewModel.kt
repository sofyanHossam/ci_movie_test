package com.example.movies.presention.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI States
sealed class MovieDetailsUiState {
    object Loading : MovieDetailsUiState()
    data class Success(val movie: MovieDetails) : MovieDetailsUiState()
    data class Error(val message: String) : MovieDetailsUiState()
}

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun loadMovieDetails(movieId: Int) {
        _uiState.value = MovieDetailsUiState.Loading
        viewModelScope.launch {
            try {
                val movie = getMovieDetailsUseCase(movieId)
                _uiState.value = MovieDetailsUiState.Success(movie)
            } catch (e: Exception) {
                _uiState.value = MovieDetailsUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
