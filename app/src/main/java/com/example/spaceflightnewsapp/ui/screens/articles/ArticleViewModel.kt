package com.example.spaceflightnewsapp.ui.screens.articles


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
import com.example.spaceflightnewsapp.data.ArticlesRepository
import com.example.spaceflightnewsapp.data.api.SpaceflightNewsApi
import com.example.spaceflightnewsapp.data.models.Article
import kotlinx.coroutines.launch
import okio.IOException


sealed interface ArticleUiState {
    data class Success(val articles: List<Article>) : ArticleUiState
    object Error : ArticleUiState
    object Loading : ArticleUiState
}


class ArticleViewModel(private val articlesRepository: ArticlesRepository): ViewModel() {

    var articleUiState: ArticleUiState by mutableStateOf(ArticleUiState.Loading)
        private set

    var articleDetailUiState: ArticleUiState by mutableStateOf(ArticleUiState.Loading)
        private set


    init {
        getArticles() // Fetch articles when the ViewModel is initialized
    }


    fun getArticles() {
        viewModelScope.launch {
            articleUiState = ArticleUiState.Loading
            articleUiState = try {
                ArticleUiState.Success(articlesRepository.getArticles())
            }catch (e:IOException) {
                ArticleUiState.Error
            }catch (e:HttpException) {
                ArticleUiState.Error
            }
        }
    }

    fun getArticleDetails(id: Int) {
        viewModelScope.launch {
            articleDetailUiState = ArticleUiState.Loading
            try {
                val article = articlesRepository.getArticleDetails(id)
                articleDetailUiState = ArticleUiState.Success(listOf(article))
            } catch (e: IOException) {
                articleDetailUiState = ArticleUiState.Error
            } catch (e: HttpException) {
                articleDetailUiState = ArticleUiState.Error
            } catch (e: Exception) {
                articleDetailUiState = ArticleUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaceflightNewsApplication)
                val articlesRepository = application.container.articlesRepository
                ArticleViewModel(articlesRepository = articlesRepository)
            }
        }
    }
}
