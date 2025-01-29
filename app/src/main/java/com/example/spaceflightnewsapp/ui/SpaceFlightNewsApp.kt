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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.spaceflightnewsapp.ui.screens.articles.ArticleViewModel
import com.example.spaceflightnewsapp.ui.screens.articles.ArticlesScreen
import com.example.spaceflightnewsapp.ui.theme.SpaceFlightNewsAppTheme


enum class AppScreen(val title: String) {
    Articles(title = "Articles"),
    FavouriteArticles(title = "Favorites"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
navController: NavController = rememberNavController()
) {

    val articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier.padding(top = 10.dp),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(modifier = Modifier.width(100.dp),
                            onClick = {}) {
                            Text("Previous")
                        }

                        Button(modifier = Modifier.width(100.dp), onClick = { }) {
                            Text("Next")
                        }
                    }
                }
            )

        },
        bottomBar = {
            BottomAppBar(

            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ArticlesScreen(
                articleUiState = articleViewModel.articleUiState,
                retryAction = articleViewModel::getArticles,
                modifier = Modifier.fillMaxSize(),
                contentPadding = it
            )
        }
    }
}

@Composable
fun BottomAppBar(navController: NavController
) {
    androidx.compose.material3.BottomAppBar(
        actions = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {

                IconButton(onClick = { navController.navigate("news") }) {
                    Icon(Icons.Filled.Home, contentDescription = "News", modifier = Modifier.size(50.dp))
                }
                IconButton(onClick = {navController.navigate("favorites")}) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorites", modifier = Modifier.size(50.dp))
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SpaceFlightNewsAppTheme {
        MainScreen()
    }
}