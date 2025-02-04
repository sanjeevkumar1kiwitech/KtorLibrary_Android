package com.madept.ktornetworkinglibraryapp.networking

import android.util.Log
import com.madept.ktornetworkinglibraryapp.model.response.Comment
import com.madept.ktornetworkinglibraryapp.model.response.Post
import com.madept.ktornetworkinglibraryapp.model.response.User
import io.ktor.client.request.*
import io.ktor.client.call.*
import kotlinx.serialization.json.Json

class ApiService {
    val baseUrl = KtorClient.baseUrl
    private val client = KtorClient.client

    suspend fun getPosts(): ApiResult<List<Post>> {
        return safeApiCall {
            client.get("${baseUrl}posts").body<List<Post>>()
        }
    }

    suspend fun getUsers(): ApiResult<List<User>> {
        return safeApiCall {
            client.get("${baseUrl}users").body<List<User>>()
        }
    }

    suspend fun getComments(): ApiResult<List<Comment>> {
        return safeApiCall {
            client.get("${baseUrl}comments").body<List<Comment>>()
        }
    }
}
