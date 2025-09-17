package com.example.movies.data.repo

import com.example.movies.data.mapper.toDomain
import com.example.movies.data.remote.MovieApi
import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val apiService: MovieApi,
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val response = apiService.getMovieDetails(movieId, "8cb3dd51149857e590d0542b897ccf4b")
        return response.toDomain() // map Dto → Domain
    }
}
