package com.beginning.tugas4c.ui

import PostViewModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.beginning.tugas4c.R
import com.beginning.tugas4c.data.repository.PostRepository


class PostsListActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

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

        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = adapter
    }

    private fun loadPosts() {
        progressBar.visibility = View.VISIBLE
        viewModel.loadPosts(
            onSuccess = {
                progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            },
            onError = { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }
        )
    }
}

class ViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}