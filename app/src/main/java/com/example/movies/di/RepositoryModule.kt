package com.example.movies.di

import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.remote.MovieApi
import com.example.movies.data.repo.MovieDetailsRepositoryImpl
import com.example.movies.data.repo.MovieRepositoryImpl
import com.example.movies.domain.repo.MovieRepository
import com.example.movies.domain.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApi,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(api,movieDao)
    }
    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        api: MovieApi
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(api)
    }
}