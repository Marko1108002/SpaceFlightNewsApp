package com.example.spaceflightnewsapp.ui.screens.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.ui.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ArticleViewModel,
    navController: NavController
) {
    val articleUiState = viewModel.articleUiState

    when (articleUiState) {
        is ArticleUiState.Loading -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading...")
            }
        }

        is ArticleUiState.Success -> {
            ArticlesListScreen(
                articles = articleUiState.articles,
                modifier = modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                contentPadding = contentPadding,
                navController = navController
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
fun ArticleCard(article: Article,navController: NavController) {
    Card(
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
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = article.image_url
            )

        }
    }
}


@Composable
private fun ArticlesListScreen(
    navController: NavController,
    articles: List<Article>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
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
            ArticleCard(article = article, navController = navController)
        }
    }


}

