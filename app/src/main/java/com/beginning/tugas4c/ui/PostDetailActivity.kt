package com.beginning.tugas4c.ui

import PostViewModel
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beginning.tugas4c.R
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.beginning.tugas4c.data.repository.PostRepository
import com.beginning.tugas4c.data.model.Post

class PostDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val postId = intent.getIntExtra("POST_ID", -1)
        if (postId == -1) {
            finish()
            return
        }

        setupViewModel()
        loadPost(postId)
    }

    private fun setupViewModel() {
        val repository = PostRepository()
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
    }

    private fun loadPost(postId: Int) {
        progressBar.visibility = View.VISIBLE
        viewModel.getPostById(
            postId,
            onSuccess = { post ->
                progressBar.visibility = View.GONE
                displayPost(post)
            },
            onError = { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun displayPost(post: Post) {
        titleTextView.text = post.title
        bodyTextView.text = post.body
        userIdTextView.text = "User ID: ${post.userId}"
        idTextView.text = "Post ID: ${post.id}"
    }
}