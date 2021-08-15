package com.roberthmdz.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roberthmdz.newsapp.models.News
import com.roberthmdz.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    private val _news = MutableLiveData<List<News>>()
    fun getNews(): LiveData<List<News>> {
        // Call a suspend function type
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews("US")
            _news.postValue(news)
        }
        return _news
    }
}