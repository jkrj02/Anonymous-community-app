package com.example.anonymous_community_app.data

data class Post(
    val postId: Int = 0,
    val typeId: Int = 0,
    val userId: Int = 0,
    val userName: String = "",
    val content: String = "",
    val imageNum: Int = 0,
    val commentCount: Int = 0,
    val likeCount: Int = 0,
    val dislikeCount: Int = 0,
    val time: Int = 0
)
