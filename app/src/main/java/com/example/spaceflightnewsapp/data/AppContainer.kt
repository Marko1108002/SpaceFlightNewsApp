package com.example.spaceflightnewsapp.data

import com.example.spaceflightnewsapp.data.api.SpaceflightNewsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val articlesRepository: ArticlesRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: SpaceflightNewsApi by lazy {
        retrofit.create(SpaceflightNewsApi::class.java)
    }

    override val articlesRepository: ArticlesRepository by lazy {
        DefaultArticleRepository(retrofitService)
    }
}
