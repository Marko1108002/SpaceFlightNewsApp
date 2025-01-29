package com.example.spaceflightnewsapp.ui

import android.adservices.topics.Topic
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleDetailScreen
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleViewModel
import com.example.spaceflightnewsapp.ui.screens.articles.ArticlesScreen
import com.example.spaceflightnewsapp.ui.screens.favorites.FavoriteArticles
import com.example.spaceflightnewsapp.ui.theme.SpaceFlightNewsAppTheme


enum class AppScreen(val title: String) {
    Articles(title = "Articles"),
    FavoriteArticles(title = "Favorites"),
    ArticleDetails("Article Details")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = when (backStackEntry?.destination?.route) {
        AppScreen.Articles.name -> AppScreen.Articles
        AppScreen.FavoriteArticles.name -> AppScreen.FavoriteArticles
        else -> AppScreen.Articles
    }
    val articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory)
    Scaffold(
        modifier = Modifier.fillMaxSize(),


        bottomBar = {
            com.example.spaceflightnewsapp.ui.BottomAppBar(currentScreen = currentScreen,
                onNewsClick = { navController.navigate(AppScreen.Articles.name) },
                onFavoritesClick = { navController.navigate(AppScreen.FavoriteArticles.name) }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreen.Articles.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Articles.name) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticlesScreen(
                        retryAction = articleViewModel::getArticles,
                        modifier = Modifier.fillMaxSize(),
                        viewModel = articleViewModel,
                        navController = navController
                    )
                }
            }
            composable(route = AppScreen.FavoriteArticles.name) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavoriteArticles()
                }
            }
            composable(
                route = "articleDetails/{articleId}",
                arguments = listOf(navArgument("articleId") { type = NavType.StringType })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
                ArticleDetailScreen(articleId = articleId.toInt(), viewModel = articleViewModel)
            }

        }

    }
}

@Composable
fun BottomAppBar(
    currentScreen: AppScreen,
    onNewsClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    androidx.compose.material3.BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onNewsClick) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "News",
                        modifier = Modifier.size(50.dp)
                    )
                }
                Text((currentScreen.title))
                IconButton(onClick = onFavoritesClick) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorites",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SpaceFlightNewsAppTheme {
        NewsApp()
    }
}