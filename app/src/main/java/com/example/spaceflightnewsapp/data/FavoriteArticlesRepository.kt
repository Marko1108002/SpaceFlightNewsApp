package com.example.spaceflightnewsapp.data

import com.example.spaceflightnewsapp.data.database.FavoriteArticle

import kotlinx.coroutines.flow.Flow


interface FavoriteArticlesRepository {
    suspend fun addFavorite(article: FavoriteArticle)
    suspend fun getFavorites(): Flow<List<FavoriteArticle>>
    suspend fun removeFavorite(article: FavoriteArticle)
}