package com.example.anonymous_community_app.server

import com.example.anonymous_community_app.data.Course
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.data.RestResponse
import com.example.anonymous_community_app.data.User
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object Interface {

    suspend fun signIn(userName: String, password: String): RestResponse {
        val call = ServerUtils.createPostCall(NetworkSettings.USER_LOGIN, User(userName, password))
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val data = ObjectMapper().readValue(response.body!!.string(), User::class.java)
                        ServerUtils.printTag(data.toString())
                        it.resume(RestResponse(1, data))
                    } else {
                        it.resume(RestResponse(2, null))
                    }
                }
            })
        }
    }

    suspend fun signUp(user: User): RestResponse {
        val call = ServerUtils.createPostCall(NetworkSettings.USER_CREATE, user)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val data = ObjectMapper().readValue(response.body!!.string(), User::class.java)
                        ServerUtils.printTag(data.toString())
                        it.resume(RestResponse(1, data))
                    } else {
                        it.resume(RestResponse(2, null))
                    }
                }
            })
        }
    }

    suspend fun updateMe(user: User): RestResponse {
        val call = ServerUtils.createPutCall(NetworkSettings.USER_UPDATE, user)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(ResponseCode.REQUEST_FAILED, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val data = body.string()
                            it.resume(RestResponse(1, data))
                        } else {
                            it.resume(RestResponse(2, null))
                        }
                    }
                }
            })
        }
    }

    suspend fun getCourseList(): RestResponse {
        val call = ServerUtils.createGetCall(NetworkSettings.COURSE_GET, null)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val data = ObjectMapper().readValue(body.string(), object :  TypeReference<List<Course>>(){})
                            ServerUtils.printTag(data.toString())
                            it.resume(RestResponse(1, data))
                        } else {
                            it.resume(RestResponse(2, null))
                        }
                    }
                }
            })
        }
    }

    suspend fun getPostList(url: String, data: Any? = null): RestResponse {
        val call = ServerUtils.createGetCall(url, data)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val data = ObjectMapper().readValue(body.string(), object :  TypeReference<List<Post>>(){})
                            ServerUtils.printTag(data.toString())
                            it.resume(RestResponse(1, data))
                        } else {
                            it.resume(RestResponse(2, null))
                        }
                    }
                }
            })
        }
    }

    suspend fun searchPost(key: String?): RestResponse {
        val call = ServerUtils.createGetCall(NetworkSettings.POST_SEARCH, key)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val data = ObjectMapper().readValue(body.string(), object :  TypeReference<List<Post>>(){})
                            ServerUtils.printTag(data.toString())
                            it.resume(RestResponse(1, data))
                        } else {
                            it.resume(RestResponse(2, null))
                        }
                    }
                }
            })
        }
    }

    suspend fun getMyPost(key: Int?): RestResponse {
        val call = ServerUtils.createGetCall(NetworkSettings.MY_POST_GET, key)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val data = ObjectMapper().readValue(body.string(), object :  TypeReference<List<Post>>(){})
                            ServerUtils.printTag(data.toString())
                            it.resume(RestResponse(1, data))
                        }
                    } else {
                        it.resume(RestResponse(2, null))
                    }
                }
            })
        }
    }

    suspend fun deleteMyPost(key: Int?): RestResponse {
        val call = ServerUtils.createDeleteCall(NetworkSettings.POST_DELETE, "postid", key)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val data = response.body!!.string()
                        it.resume(RestResponse(1, data))
                    } else {
                        it.resume(RestResponse(2, null))
                    }
                }
            })
        }
    }

    suspend fun postPublish(post: Post): RestResponse {
        val call = ServerUtils.createPostCall(NetworkSettings.POST_CREATE, post)
        return suspendCoroutine {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    ServerUtils.printTag("send message fail!")
                    it.resume(RestResponse(0, null))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val data = response.body!!.string()
                        ServerUtils.printTag(data)
                        it.resume(RestResponse(1, data))
                    } else {
                        it.resume(RestResponse(2, null))
                    }
                }
            })
        }
    }
}