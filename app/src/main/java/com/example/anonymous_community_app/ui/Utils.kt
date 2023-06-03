package com.example.anonymous_community_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.server.ServerUtils
import java.nio.channels.Selector
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Utils {
    private val postSort0: Comparator<Post> = Comparator { o1, o2 ->  o2.time - o1.time }
    private val postSort1: Comparator<Post> = Comparator { o1, o2 ->
        if (o2.commentCount == o1.commentCount) {
            o2.likeCount - o1.likeCount
        } else {
            o2.commentCount - o1.commentCount
        }
    }
    private val postSort2: Comparator<Post> = Comparator { o1, o2 ->
        if (o2.likeCount == o1.likeCount) {
            o1.dislikeCount - o2.dislikeCount
        } else {
            o2.likeCount - o1.likeCount
        }
    }

    fun sortPost(postList: List<Post>, type: Int, sortType: Int) : List<Post> {
        val result: MutableList<Post> = postList.toMutableList()
        for (l in postList) {
            if (l.typeId != type) {
                result.remove(l)
            }
        }
        result.sortWith(
            when (sortType) {
                0 -> postSort0
                1 -> postSort1
                else -> postSort2
            }
        )
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun calPostTime(timestamp: Int): String {
        val nowTimestamp = System.currentTimeMillis() / 1000
        val past = nowTimestamp - timestamp
        return if (past in 1..59) {
            "${past}秒前"
        } else if (past in 60..600) {
            "${past/60}分钟前"
        } else {
            val date = Date((timestamp * 1000).toLong()) // 将时间戳转换为毫秒
            val format = SimpleDateFormat("yyyy-MM-dd") // 定义日期时间格式
            format.format(date).toString()
        }
    }

    fun getPostType(type: Int): String {
        return when (type) {
            0 -> "生活分享"
            1 -> "信息互换"
            else -> "匿名树洞"
        }
    }

    fun getPostTypeId(type: String): Int {
        return when (type) {
            "生活分享" -> 0
            "信息互换" -> 1
            else -> 2
        }
    }

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun printLog(message: String) {
        Log.i("TAG", message)
    }
}