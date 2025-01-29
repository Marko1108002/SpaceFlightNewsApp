package com.example.spaceflightnewsapp.data

import com.example.spaceflightnewsapp.data.api.SpaceflightNewsApi
import com.example.spaceflightnewsapp.data.models.Article

interface ArticlesRepository {
    suspend fun getArticles(): List<Article>
}


class DefaultArticleRepository(
    private val articlesApiService: SpaceflightNewsApi
) : ArticlesRepository {

    private var currentPage = 1
    private val pageSize = 10

    override suspend fun getArticles(): List<Article> {
        val response = articlesApiService.getArticles()
        return response.results
    }

    // Load the next page of articles
    suspend fun loadNextPage(): List<Article> {
        currentPage++
        return getArticles()
    }

    // Load the previous page of articles
    suspend fun loadPreviousPage(): List<Article> {
        if (currentPage > 1) {
            currentPage--
        }
        return getArticles()
    }
}