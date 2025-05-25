package com.beginning.tugas4c.ui

import PostViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.beginning.tugas4c.data.repository.PostRepository
import com.beginning.tugas4c.databinding.ActivityPostsListBinding

class PostsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsListBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        loadPosts()
    }

    private fun setupViewModel() {
        val repository = PostRepository()
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
    }

    private fun setupRecyclerView() {
        adapter = PostsAdapter(viewModel.posts) { post ->
            val intent = Intent(this, PostDetailActivity::class.java)
            intent.putExtra("POST_ID", post.id)
            startActivity(intent)
        }

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.postsRecyclerView.adapter = adapter
    }

    private fun loadPosts() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.loadPosts(
            onSuccess = {
                binding.progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            },
            onError = { error ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }
        )
    }
}