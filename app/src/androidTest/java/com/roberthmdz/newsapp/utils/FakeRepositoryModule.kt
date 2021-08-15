package com.roberthmdz.newsapp.utils

import com.roberthmdz.newsapp.di.RepositoryModule
import com.roberthmdz.newsapp.models.News
import com.roberthmdz.newsapp.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.*
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    // Inject dependency
    @Provides
    @Singleton
    fun providerNewsRepository(): NewsRepository = object  : NewsRepository {

        val news = arrayListOf(
            News(title = "Title 1", content = "Content description", author = "", url = "", urlToImage = "", publishedAt = "", description = ""),
            News(title = "Title 2", content = "Content description", author = "", url = "", urlToImage = "", publishedAt = "", description = ""),
        )

        override suspend fun getNews(country: String): List<News> {
            return news
        }

        override fun getNew(title: String): News {
            return news[0]
        }
    }

}