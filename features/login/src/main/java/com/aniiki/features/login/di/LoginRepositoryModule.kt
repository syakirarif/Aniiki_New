package com.aniiki.features.login.di

import com.aniiki.features.login.repository.LoginRepository
import com.aniiki.features.login.repository.LoginRepositoryImpl
import com.syakirarif.aniiki.apiservice.di.ApiServiceModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiServiceModule::class])
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepository: LoginRepositoryImpl): LoginRepository
}