package com.example.movies.presention

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MovieDetails : Screen("movie_details/{movieId}") {
        fun createRoute(movieId: Int) = "movie_details/$movieId"
    }
}
