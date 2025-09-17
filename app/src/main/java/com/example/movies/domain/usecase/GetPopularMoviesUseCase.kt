package com.example.movies.domain.usecase


import com.example.movies.domain.repo.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1) = repository.getPopularMovies(page)
}
