package com.example.spaceflightnewsapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val image_url: String,
    val news_site: String,
    val summary: String,
    val published_at: String
)
