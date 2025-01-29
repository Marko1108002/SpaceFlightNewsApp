package com.example.spaceflightnewsapp.ui.screens.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spaceflightnewsapp.data.models.Article

@Composable
fun ArticleDetailScreen(
    articleId: Int, viewModel: ArticleViewModel
) {

    val articleUiState = viewModel.articleDetailUiState
    when (articleUiState) {
        is ArticleUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading...")
            }
        }

        is ArticleUiState.Success -> {
            articleUiState.articles.find { it.id == articleId }
                ?.let { ArticleDetails(article = it) }
        }

        is ArticleUiState.Error -> {
            Text(text = "Error loading article", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun ArticleDetails(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.image_url)
                .crossfade(true)
                .build(),
            contentDescription = article.image_url
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = article.summary)
    }
}




