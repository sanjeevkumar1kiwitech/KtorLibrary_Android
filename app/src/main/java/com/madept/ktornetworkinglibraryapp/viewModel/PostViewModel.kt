package com.madept.ktornetworkinglibraryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madept.ktornetworkinglibraryapp.model.response.Post
import com.madept.ktornetworkinglibraryapp.networking.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.madept.ktornetworkinglibraryapp.networking.ApiResult

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val apiService = ApiService()

    private val _posts = MutableStateFlow<List<Post>>(emptyList()) // Default empty list
    val posts: StateFlow<List<Post>> = _posts.asStateFlow() // Expose as immutable flow

    private val _error = MutableStateFlow<String?>(null) // Default null
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchPosts() {
        viewModelScope.launch {
            when (val result = apiService.getPosts()) {
                is ApiResult.Success -> _posts.value = result.data
                is ApiResult.Error -> _error.value = result.message
            }
        }
    }
}
