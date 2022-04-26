package dev.mrbe.kmmapp.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.mrbe.kmmapp.Posts

class PostsRvAdapter (var posts: List<Posts>)
    : RecyclerView.Adapter<PostsRvAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsRvAdapter.PostViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
            .run (::PostViewHolder)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindData(posts[position]) }

    override fun getItemCount(): Int = posts.count()

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindData(post: Posts){

        }
    }
}