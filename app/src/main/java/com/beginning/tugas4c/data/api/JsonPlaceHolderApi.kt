package com.beginning.tugas4c.data.api

import com.beginning.tugas4c.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderApi {

    interface JsonPlaceholderApi {
        @GET("posts")
        suspend fun getPosts(): List<Post>

        @GET("posts/{id}")
        suspend fun getPostById(@Path("id") id: Int): Post
    }
}