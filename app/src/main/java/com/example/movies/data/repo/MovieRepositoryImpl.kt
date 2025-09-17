package com.example.movies.data.repo


import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.entity.toDto
import com.example.movies.data.local.entity.toEntity
import com.example.movies.data.remote.MovieApi
import com.example.movies.data.remote.dto.MovieResponse
import com.example.movies.domain.repo.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): MovieResponse {
        val cached = movieDao.getAllMovies()
        return if (cached.isNotEmpty()) {
            MovieResponse(
                page = page,
                results = cached.map { it.toDto() },
                total_pages = 1,
                total_results = cached.size
            )
        } else {
            val response = api.getPopularMovies(
                apiKey = "8cb3dd51149857e590d0542b897ccf4b",
                page = page,
                language = "en-US"
            )
            movieDao.clearMovies() // حذف القديم قبل الحفظ الجديد
            movieDao.insertMovies(response.results.map { it.toEntity() })
            response
        }
    }
}

