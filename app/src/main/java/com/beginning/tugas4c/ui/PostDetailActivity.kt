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
import com.beginning.tugas4c.databinding.ActivityPostDetailBinding
import com.beginning.tugas4c.databinding.ActivityPostsListBinding

class PostDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel
    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getPostById(
            postId,
            onSuccess = { post ->
                binding.progressBar.visibility = View.GONE
                displayPost(post)
            },
            onError = { error ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun displayPost(post: Post) {
        binding.titleTextView.text = post.title
        binding.bodyTextView.text = post.body
        binding.userIdTextView.text = "User ID: ${post.userId}"
        binding.idTextView.text = "Post ID: ${post.id}"
    }
}