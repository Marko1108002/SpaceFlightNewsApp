package com.example.spaceflightnewsapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavoriteArticle)

    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<FavoriteArticle>

    @Delete
    suspend fun deleteFavorite(article: FavoriteArticle)
}