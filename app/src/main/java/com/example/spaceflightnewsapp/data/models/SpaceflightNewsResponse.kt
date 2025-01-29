package com.example.spaceflightnewsapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SpaceflightNewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Article>
)
