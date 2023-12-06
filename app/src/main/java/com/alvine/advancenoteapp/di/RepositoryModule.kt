package com.alvine.advancenoteapp.di

import com.alvine.advancenoteapp.data.local.repository.RepositoryImpl
import com.alvine.advancenoteapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsRepository(repositoryImpl: RepositoryImpl):Repository
    }