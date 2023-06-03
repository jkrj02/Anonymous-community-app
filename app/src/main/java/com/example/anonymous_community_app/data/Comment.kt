package com.example.anonymous_community_app.data

data class Comment(
    val commentId: Int = 0,
    val postId: Int = 0,
    val objectId: Int = 0,
    val userId: Int = 0,
    val userName: String = "",
    val otherName: String = "",
    val content: String = "",
    val commentCount: Int = 0,
    val likeCount: Int = 0,
    val dislikeCount: Int = 0,
    val time: String = "",
    val valid: Boolean = false,
    val courseId: Int = 0
)
