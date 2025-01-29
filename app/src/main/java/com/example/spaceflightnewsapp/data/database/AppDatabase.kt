package com.example.spaceflightnewsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}