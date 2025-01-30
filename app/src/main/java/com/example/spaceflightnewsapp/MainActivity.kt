package com.example.spaceflightnewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier

import androidx.navigation.compose.rememberNavController
import com.example.spaceflightnewsapp.ui.theme.SpaceFlightNewsAppTheme

import com.example.spaceflightnewsapp.ui.NewsApp


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceFlightNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NewsApp(navController = navController)
                }
            }
        }
    }
}


