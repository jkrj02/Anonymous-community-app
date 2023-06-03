package com.example.anonymous_community_app.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.ui.Utils

class PostAdapter(val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //获取子条目的布局控件ID
        val userName: TextView
        val time: TextView
        val type: TextView
        val content: TextView
        val likeCount: TextView
        val dislikeCount: TextView
        val commentCount: TextView

        init {
            userName = view.findViewById(R.id.username)
            time = view.findViewById(R.id.time)
            type = view.findViewById(R.id.type)
            content = view.findViewById(R.id.content)
            likeCount = view.findViewById(R.id.like_num)
            dislikeCount = view.findViewById(R.id.dislike_num)
            commentCount = view.findViewById(R.id.comment_num)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_recycleview_item, parent, false)

        view.setOnClickListener {

        }
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, p0: Int) {
        holder.userName.text = postList[p0].userName
        holder.time.text = Utils.calPostTime(postList[p0].time)
        holder.type.text = Utils.getPostType(postList[p0].typeId)
        holder.content.text = postList[p0].content
        holder.likeCount.text = postList[p0].likeCount.toString()
        holder.dislikeCount.text = postList[p0].dislikeCount.toString()
        holder.commentCount.text = postList[p0].commentCount.toString()
    }

}