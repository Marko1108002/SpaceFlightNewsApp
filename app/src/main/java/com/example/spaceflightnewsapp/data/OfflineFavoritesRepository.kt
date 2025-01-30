package com.example.spaceflightnewsapp.data

import com.example.spaceflightnewsapp.data.database.FavoriteArticle
import com.example.spaceflightnewsapp.data.database.FavoriteDao
import kotlinx.coroutines.flow.Flow

class OfflineFavoritesRepository(private val favoriteDao: FavoriteDao): FavoriteArticlesRepository {
    override suspend fun getFavorites(): Flow<List<FavoriteArticle>> = favoriteDao.getFavorites()
    override suspend fun addFavorite(article: FavoriteArticle) = favoriteDao.insertFavorite(article)
    override suspend fun removeFavorite(article: FavoriteArticle) = favoriteDao.deleteFavorite(article)

}