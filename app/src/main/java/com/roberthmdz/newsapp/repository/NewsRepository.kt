package com.roberthmdz.newsapp.repository

import com.roberthmdz.newsapp.models.News
import com.roberthmdz.newsapp.provider.NewsProvider
import javax.inject.Inject

interface NewsRepository {

    suspend fun getNews(country: String) : List<News>

    fun getNew(title: String) : News
}

class NewsRepositoryImpl @Inject constructor(
    private val newsProvider: NewsProvider
): NewsRepository {

    private var news: List<News> = emptyList()
    override suspend fun getNews(country: String): List<News> {
        val apiResponse = newsProvider.topHeadLines(country).body()
        if(apiResponse?.status == "error") {
            when (apiResponse.code) {
                "apiKeyMissing" -> throw MissingApiKeyException()
                "apiKeyInvalid" -> throw ApiKeyInvalidException()
                else -> throw Exception("An error occurred while trying to get the Top Headlines News")

            }

        }
        news = apiResponse?.articles ?: emptyList()
        return news;
    }

    override fun getNew(title: String): News {
       return news.first {it.title == title}
    }

}

class MissingApiKeyException : java.lang.Exception()
class ApiKeyInvalidException : java.lang.Exception()