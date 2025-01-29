package com.example.spaceflightnewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spaceflightnewsapp.ui.theme.SpaceFlightNewsAppTheme
import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.data.models.Author
import com.example.spaceflightnewsapp.ui.MainScreen
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleUiState
import com.example.spaceflightnewsapp.ui.screens.articles.ArticlesScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceFlightNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    val mockArticles = listOf(
        Article(
            id = 1, title = "Mock Article 1", summary = "Summary 1",
            authors = listOf(Author("Aloy")),
            url = "asdasda",
            image_url = "asdasda",
            news_site = "asdasda",
            published_at = "asdasdas"
        ),
        Article(
            id = 1, title = "Mock Article 2", summary = "Summary 1",
            authors = listOf(Author("Aloy")),
            url = "asdasda",
            image_url = "asdasda",
            news_site = "asdasda",
            published_at = "asdasdas"
        ),
    )

    SpaceFlightNewsAppTheme {
        ArticlesScreen(
            articleUiState = ArticleUiState.Success(mockArticles),
            retryAction = {}
        )
    }
}


