package com.beginning.tugas4c.data.repository

import com.beginning.tugas4c.data.api.RetrofitInstance
import com.beginning.tugas4c.data.model.Post

class PostRepository {
    private val api = RetrofitInstance.api

    suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }

    suspend fun getPostById(id: Int): Post {
        return api.getPostById(id)
    }
}