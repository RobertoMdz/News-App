package com.roberthmdz.newsapp

import com.roberthmdz.newsapp.provider.NewsProvider
import com.roberthmdz.newsapp.repository.ApiKeyInvalidException
import com.roberthmdz.newsapp.repository.MissingApiKeyException
import com.roberthmdz.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets

class NewsRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val newsProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsProvider::class.java)

    private val newsRepository = NewsRepositoryImpl(newsProvider)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Top headlines response is correct`() {
        mockWebServer.equeueResponse("top_headlines.json")

        // Execute repository function
        runBlocking {
            val articles = newsRepository.getNews("US")
            assertEquals(2, articles.size)
            assertEquals("Christina Maxouris, CNN", articles[0].author)
            assertEquals("louis.allwood@thesun.co.uk", articles[1].author)
        }
    }

    @Test
    fun `Api key missing exception`() {
        mockWebServer.equeueResponse("api_key_missing.json")

        // Verify that an exception was thrown
        assertThrows(MissingApiKeyException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

    @Test
    fun `Invalid API key exception`() {
        mockWebServer.equeueResponse("api_key_invalid.json")

        // Verify that an exception was thrown
        assertThrows(ApiKeyInvalidException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

}

// It is an extension for MockWebServer class
fun MockWebServer.equeueResponse(filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}