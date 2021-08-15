package com.roberthmdz.newsapp.di

import com.roberthmdz.newsapp.provider.NewsProvider
import com.roberthmdz.newsapp.repository.NewsRepository
import com.roberthmdz.newsapp.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providerNewsRepository(provider: NewsProvider): NewsRepository = NewsRepositoryImpl(provider)
}