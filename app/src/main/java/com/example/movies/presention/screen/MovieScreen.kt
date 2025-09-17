package com.example.movies.presention.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.presention.MovieItem
import com.example.movies.presention.MovieUiState
import com.example.movies.presention.viewmodel.MovieViewModel

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    when (state) {
        is MovieUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize().testTag("loading_indicator"))
        }
        is MovieUiState.Success -> {
            LazyColumn {
                items(items = state.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
        is MovieUiState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color.Red,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
