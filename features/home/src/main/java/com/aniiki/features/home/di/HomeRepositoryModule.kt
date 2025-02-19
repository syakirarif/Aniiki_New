package com.aniiki.features.home.di

import com.aniiki.features.home.repository.DetailRepository
import com.aniiki.features.home.repository.HomeRepository
import com.aniiki.features.home.repository.PeopleDetailRepository
import com.aniiki.features.home.repository.ScheduleRepository
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeRepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideHomeRepositoryModule(
        animeEndpoints: AnimeEndpoints
    ): HomeRepository {
        return HomeRepository(animeEndpoints)
    }

    @Provides
    @ViewModelScoped
    fun provideScheduleRepositoryModule(
        animeEndpoints: AnimeEndpoints
    ): ScheduleRepository {
        return ScheduleRepository(animeEndpoints)
    }

    @Provides
    @ViewModelScoped
    fun provideDetailRepositoryModule(
        animeEndpoints: AnimeEndpoints
    ): DetailRepository {
        return DetailRepository(animeEndpoints)
    }

    @Provides
    @ViewModelScoped
    fun providePeopleDetailRepositoryModule(
        animeEndpoints: AnimeEndpoints
    ): PeopleDetailRepository {
        return PeopleDetailRepository(animeEndpoints)
    }
}