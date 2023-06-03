package com.example.anonymous_community_app.ui.me

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MePublicationAdapter(
    val context: Context,
    val viewModel: MainViewModel,
    val postList: List<Post>) : RecyclerView.Adapter<MePublicationAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //获取子条目的布局控件ID
        val title: TextView
        val delete: TextView

        init {
            title = view.findViewById(R.id.me_publication_item_title)
            delete = view.findViewById(R.id.me_publication_item_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.me_publication_item, parent, false)

        view.setOnClickListener {

        }
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PostViewHolder, p0: Int) {
        holder.title.text = "发布${p0+1}"
        holder.delete.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                var restResponse = Interface.deleteMyPost(postList[p0].postId)
                if (restResponse.code == 1) {
                    Utils.showShortToast(context, "删除成功")
                    restResponse = Interface.getMyPost(viewModel.user.value!!.userId)
                    if (restResponse.code == 1) {
                        viewModel.myPostList.postValue(restResponse.data as List<Post>?)
                    }
                }
            }
        }
    }

}