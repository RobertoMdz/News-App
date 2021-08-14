package com.roberthmdz.newsapp.provider

import com.roberthmdz.newsapp.models.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "3e88d75f549d40eda3d3b26eaacd0060"

interface NewsProvider {

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun topHeadLines(@Query("country") country: String) : Response<NewsApiResponse>

}