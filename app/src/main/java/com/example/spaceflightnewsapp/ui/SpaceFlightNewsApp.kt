package com.example.spaceflightnewsapp.ui

import android.adservices.topics.Topic
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
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
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.data.models.Article
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleDetailScreen
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleViewModel
import com.example.spaceflightnewsapp.ui.screens.articles.ArticlesScreen
import com.example.spaceflightnewsapp.ui.screens.favorites.FavoriteArticlesScreen
import com.example.spaceflightnewsapp.ui.screens.favorites.FavoriteViewModel
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
        else -> if (backStackEntry?.destination?.route?.startsWith("articleDetails") == true) {
            AppScreen.ArticleDetails
        } else {
            AppScreen.Articles
        }
    }
    val articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory)

    val favoriteViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModel.Factory)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(0xFF23486A),
                    scrolledContainerColor = Color(0xFF23486A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color(0xFF23486A),
                    actionIconContentColor = Color(0xFF23486A)
                )
            )
        },
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
                        navController = navController,
                        favoriteViewModel = favoriteViewModel
                    )
                }
            }
            composable(route = AppScreen.FavoriteArticles.name) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavoriteArticlesScreen(
                        viewModel = favoriteViewModel,
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                        retryAction = favoriteViewModel::loadFavorites,
                        favoriteViewModel = favoriteViewModel
                    )
                }
            }
            composable(
                route = "articleDetails/{articleId}",
                arguments = listOf(navArgument("articleId") { type = NavType.StringType })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
                if (articleId != null) {
                    ArticleDetailScreen(articleId = articleId, viewModel = articleViewModel)
                } else {
                    Text(text = "Invalid Article ID")
                }

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
                        modifier = Modifier.size(50.dp), tint = Color.White
                    )
                }
                Text(
                    text = currentScreen.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                IconButton(onClick = onFavoritesClick) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorites",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
            }
        },
        containerColor = Color(0xFF23486A)
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SpaceFlightNewsAppTheme {
        NewsApp()
    }
}