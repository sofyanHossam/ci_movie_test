package com.example.movies.domain.repo

import com.example.movies.data.remote.dto.MovieResponse

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): MovieResponse
}