package com.example.movies.presention.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies.domain.model.MovieDetails
import com.example.movies.presention.viewmodel.MovieDetailsUiState
import com.example.movies.presention.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Call load when entering screen
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    when (uiState) {
        is MovieDetailsUiState.Loading -> LoadingView()
        is MovieDetailsUiState.Success -> {
            val movie = (uiState as MovieDetailsUiState.Success).movie
            MovieDetailsContent(movie)
        }
        is MovieDetailsUiState.Error -> {
            val message = (uiState as MovieDetailsUiState.Error).message
            ErrorView(message) {
                viewModel.loadMovieDetails(movieId)
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun MovieDetailsContent(movie: MovieDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterPath),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Release Date: ${movie.releaseDate}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Rating: ${movie.voteAverage}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
