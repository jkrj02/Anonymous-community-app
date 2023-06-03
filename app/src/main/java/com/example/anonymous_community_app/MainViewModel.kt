package com.example.anonymous_community_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anonymous_community_app.data.Course
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.data.User

class MainViewModel: ViewModel() {
    var user = MutableLiveData<User>()
    var courseList = MutableLiveData<List<Course>>()
    var postList = MutableLiveData<List<Post>>()
    var myPostList = MutableLiveData<List<Post>>()
}