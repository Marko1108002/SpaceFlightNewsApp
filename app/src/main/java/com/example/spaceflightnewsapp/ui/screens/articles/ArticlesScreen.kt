package com.example.spaceflightnewsapp.ui.screens.articles


import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import coil.compose.AsyncImage
import coil.request.ImageRequest

import com.example.spaceflightnewsapp.data.database.FavoriteArticle
import com.example.spaceflightnewsapp.data.models.Article

import com.example.spaceflightnewsapp.ui.screens.favorites.FavoriteViewModel

@Composable
fun ArticlesScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ArticleViewModel,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    when (val articleUiState = viewModel.articleUiState) {
        is ArticleUiState.Loading -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text("Loading...")
            }
        }

        is ArticleUiState.Success -> {
            ArticlesListScreen(
                articles = articleUiState.articles,
                modifier = modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                contentPadding = contentPadding,
                navController = navController,
                favoriteViewModel = favoriteViewModel
            )
        }

        is ArticleUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("An error occurred:")
                TextButton(onClick = retryAction) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    val favorites = viewModel.favorites.collectAsState(initial = emptyList()).value
    var isFavorite = article.id in favorites.map { it.id }

    Card(colors = CardColors(
        containerColor = Color(0xFF3B6790),
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.Gray
    ),
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            val articleId = article.id
            navController.navigate("articleDetails/$articleId")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Justify
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = article.image_url,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val favoriteArticle = FavoriteArticle(
                        id = article.id,
                        title = article.title,
                        image_url = article.image_url,
                        summary = article.summary
                    )
                    viewModel.addFavorite(favoriteArticle)
                    isFavorite = true
                },
                enabled = !isFavorite,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Color(0xFFEFB036),
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text("Add to Favorites")
            }
        }
    }
}


@Composable
private fun ArticlesListScreen(
    navController: NavController,
    articles: List<Article>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    favoriteViewModel: FavoriteViewModel
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = articles,
            key = { article ->
                article.id
            }
        ) { article ->
            ArticleCard(
                article = article,
                navController = navController,
                viewModel = favoriteViewModel
            )
        }
    }


}




