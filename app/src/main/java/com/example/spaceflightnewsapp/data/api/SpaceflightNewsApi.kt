package com.example.spaceflightnewsapp.data.api
import androidx.room.Query
import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.data.models.SpaceflightNewsResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceflightNewsApi {
    @GET("articles/")

    suspend fun getArticles(
//        @Query("page") page: Int,
//        @Query("pageSize") pageSize: Int
    ): SpaceflightNewsResponse

    @GET("articles/{id}")
    suspend fun getArticleDetails(@Path("id") id: Int): Article


}
