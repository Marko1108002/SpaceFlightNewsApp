package com.example.spaceflightnewsapp.ui.screens.articles
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spaceflightnewsapp.data.models.Article

@Composable
fun ArticleDetailScreen(
    articleId: Int, viewModel: ArticleViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(articleId) {
        viewModel.getArticleDetails(articleId)
    }

    val articleUiState by viewModel.articleDetailUiState.collectAsState()
    when (articleUiState) {
        is ArticleUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text("Loading...")
            }
        }

        is ArticleUiState.Success -> {
            (articleUiState as ArticleUiState.Success).articles.find { it.id == articleId }
                ?.let { ArticleDetails(article = it) }
        }

        is ArticleUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Error loading article", color = MaterialTheme.colorScheme.error)
            }

        }
    }
}


@Composable
fun ArticleDetails(
    article: Article
) {
    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.image_url)
                .crossfade(true)
                .build(),
            contentDescription = article.image_url,
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(article.title,
            style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        if (article.authors.isEmpty()) {
            Text(text = "No authors available", style = MaterialTheme.typography.labelMedium)
        } else {
            Text(
                text = article.authors.joinToString(", ") { it.name ?: "Unknown" },
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = article.summary, textAlign = TextAlign.Justify)
    }
}




