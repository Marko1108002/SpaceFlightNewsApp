package com.example.spaceflightnewsapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavoriteArticle)

    @Query("SELECT * FROM favorites")
     fun getFavorites(): Flow<List<FavoriteArticle>>

    @Delete
    suspend fun deleteFavorite(article: FavoriteArticle)
}