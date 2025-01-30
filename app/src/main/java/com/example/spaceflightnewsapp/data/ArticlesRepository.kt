package com.example.spaceflightnewsapp.data

import android.util.Log

import com.example.spaceflightnewsapp.data.api.SpaceflightNewsApi
import com.example.spaceflightnewsapp.data.models.Article


interface ArticlesRepository {
    suspend fun getArticles(): List<Article>

    suspend fun getArticleDetails(id: Int): Article
}


class DefaultArticleRepository(
    private val articlesApiService: SpaceflightNewsApi,
) : ArticlesRepository {


    override suspend fun getArticles(): List<Article> {
        val response = articlesApiService.getArticles()
        return response.results
    }

    override suspend fun getArticleDetails(id: Int): Article {
        try {
            val response = articlesApiService.getArticleDetails(id)
            return response
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Error fetching article details", e)
            throw e
        }
    }
}

