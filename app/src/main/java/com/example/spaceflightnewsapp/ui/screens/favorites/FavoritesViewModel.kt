package com.example.spaceflightnewsapp.ui.screens.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.spaceflightnewsapp.SpaceflightNewsApplication
import com.example.spaceflightnewsapp.data.FavoriteArticlesRepository
import com.example.spaceflightnewsapp.data.database.FavoriteArticle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed interface FavoriteUiState {
    data class Success(val articles: List<FavoriteArticle>) : FavoriteUiState
    object Error : FavoriteUiState
    object Loading : FavoriteUiState
}

class FavoriteViewModel(private val favoritesRepository: FavoriteArticlesRepository) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteArticle>>(emptyList())
    val favorites: StateFlow<List<FavoriteArticle>> get() = _favorites

    var favoriteUistate: FavoriteUiState by mutableStateOf(FavoriteUiState.Loading)
        private set


    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            favoriteUistate = FavoriteUiState.Loading
            try {
                favoritesRepository.getFavorites().collect { articles ->
                    _favorites.value = articles
                    favoriteUistate = FavoriteUiState.Success(articles)
                    }
                } catch (e:IOException) {
                    FavoriteUiState.Error
                } catch (e:HttpException) {
                    FavoriteUiState.Error
                }

            }
        }

        fun addFavorite(article: FavoriteArticle) {
            viewModelScope.launch {
                favoritesRepository.addFavorite(article)
                loadFavorites()
            }
        }

        fun removeFavorite(article: FavoriteArticle) {
            viewModelScope.launch {
                favoritesRepository.removeFavorite(article)
                loadFavorites()
            }
        }

        companion object {
            val Factory: ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val application =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaceflightNewsApplication)
                    val favoritesRepository = application.container.favoritesRepository
                    FavoriteViewModel(favoritesRepository = favoritesRepository)
                }
            }
        }
    }
