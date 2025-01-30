package com.example.spaceflightnewsapp.data.api

import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.data.models.SpaceflightNewsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceflightNewsApi {
    @GET("articles?limit=50")

    suspend fun getArticles(
    ): SpaceflightNewsResponse

    @GET("articles/{id}")
    suspend fun getArticleDetails(@Path("id") id: Int): Article


}
