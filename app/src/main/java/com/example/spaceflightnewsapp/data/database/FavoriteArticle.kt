package com.example.spaceflightnewsapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "favorites")
data class FavoriteArticle(
    @PrimaryKey val id: Int,
    val title: String,
    val image_url: String,
)