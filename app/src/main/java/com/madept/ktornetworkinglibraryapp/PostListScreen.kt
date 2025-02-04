@file:OptIn(ExperimentalMaterial3Api::class)

package com.madept.ktornetworkinglibraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.madept.ktornetworkinglibraryapp.viewModel.PostViewModel

@Composable
fun PostListScreen(modifier: Modifier = Modifier,viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ktor API Posts") }) }
    ) { padding ->

        if (error != null) {
            Text(text = "Error: $error", color = Color.Red)
        }else{
            LazyColumn(modifier = modifier.padding(padding)) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = post.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }

    }
}
