package com.example.anonymous_community_app.server

object NetworkSettings {
    private const val HOST = "8.130.124.255"
    private const val PORT = "8080"
    private const val PROTOCOL = "http"
    // 测试接口
    const val TEST = "$PROTOCOL://$HOST:$PORT/test"
    // 用户相关接口
    const val USER_LOGIN = "$PROTOCOL://$HOST:$PORT/user/login"
    const val USER_UPDATE = "$PROTOCOL://$HOST:$PORT/user/update"
    const val USER_CREATE = "$PROTOCOL://$HOST:$PORT/user/create"
    const val DELETE = "$PROTOCOL://$HOST:$PORT/delete"
    // 帖子
    const val POST_GET = "$PROTOCOL://$HOST:$PORT/post/get"
    const val MY_POST_GET = "$PROTOCOL://$HOST:$PORT/post/my"
    const val POST_SEARCH = "$PROTOCOL://$HOST:$PORT/post/search"
    const val POST_CREATE = "$PROTOCOL://$HOST:$PORT/post/create"
    const val POST_DELETE = "$PROTOCOL://$HOST:$PORT/post/delete"
    // 课程
    const val COURSE_GET = "$PROTOCOL://$HOST:$PORT/course/get"
    const val COURSE_SEARCH = "$PROTOCOL://$HOST:$PORT/course/search"
}