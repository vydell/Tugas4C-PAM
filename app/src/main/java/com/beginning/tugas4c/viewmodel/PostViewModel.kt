import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beginning.tugas4c.data.model.Post
import com.beginning.tugas4c.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    val posts = mutableListOf<Post>()

    fun loadPosts(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.getPosts()
                posts.clear()
                posts.addAll(response)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getPostById(id: Int, onSuccess: (Post) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val post = repository.getPostById(id)
                onSuccess(post)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error occurred")
            }
        }
    }
}