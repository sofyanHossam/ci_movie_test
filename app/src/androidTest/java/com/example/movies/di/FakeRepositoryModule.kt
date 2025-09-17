package com.example.movies.di


import com.example.movies.domain.repo.MovieRepository
import com.example.movies.domain.repository.MovieDetailsRepository
import com.example.movies.fakes.FakeMovieDetailsRepository
import com.example.movies.fakes.FakeMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class] // بيستبدل الموديول الأصلي
)
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideFakeMovieRepository(): MovieRepository {
        return FakeMovieRepository()
    }

    @Provides
    @Singleton
    fun provideFakeMovieDetailsRepository(): MovieDetailsRepository {
        return FakeMovieDetailsRepository()
    }
}
