package com.example.movies.domain.repository

import com.example.movies.domain.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}