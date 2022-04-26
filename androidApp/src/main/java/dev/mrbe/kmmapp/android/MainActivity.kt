package dev.mrbe.kmmapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import dev.mrbe.kmmapp.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.mrbe.kmmapp.PostsSdk
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import sqldelight.DatabaseDriverFactory

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val postsRvAdapter = PostsRvAdapter(listOf())


    //sdk
    private val sdk = PostsSdk(DatabaseDriverFactory(this))

    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Posts"
        setContentView(R.layout.activity_main)



        postsRecyclerView = findViewById(R.id.postsListRv)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)

        postsRecyclerView.adapter = postsRvAdapter
        postsRecyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayPosts(true)
        }

        displayPosts(false)

    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displayPosts(needReload: Boolean) {
        progressBarView.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                sdk.getPosts(needReload)
            }.onSuccess {
                postsRvAdapter.posts = it
                postsRvAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false

        }
    }
}
