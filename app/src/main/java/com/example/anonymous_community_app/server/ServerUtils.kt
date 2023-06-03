package com.example.anonymous_community_app.server

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.util.encoders.Hex

object ServerUtils {
    private val digest512 = Keccak.Digest512();
    private val client = OkHttpClient()
    private val mapper = ObjectMapper()
    private val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()


    fun encrypt(origin: String) =
        String(Hex.encode(digest512.digest(origin.toByteArray(Charsets.UTF_8))))

    private fun getResponseMessage(code: Int): String {
        return when (code) {
            ResponseCode.SIGN_IN_SUCCESS ->"登录成功"
            ResponseCode.SIGN_UP_SUCCESS -> "注册成功"
            ResponseCode.SIGN_IN_FAILED -> "用户名或密码错误"
            ResponseCode.SIGN_UP_FAILED -> "注册失败"
            ResponseCode.DELETE_FAILED -> "删除失败"
            ResponseCode.DELETE_SUCCESS -> "删除成功,自动退出"
            ResponseCode.UPDATE_SUCCESS -> "更新成功"
            ResponseCode.UPDATE_FAILED -> "更新失败"
            ResponseCode.EMPTY_RESPONSE -> "响应体为空"
            ResponseCode.SERVER_ERROR -> "服务器错误"
            ResponseCode.JSON_SERIALIZATION -> "JSON序列化错误"
            ResponseCode.EXIT_SUCCESS -> "退出成功"
            ResponseCode.REQUEST_FAILED -> "请求发送失败"
            ResponseCode.UNCHANGED_INFORMATION -> "未修改信息"
            else -> "待定"
        }
    }

    fun showToast(context: Context, code: Int) {
        Toast.makeText(context, getResponseMessage(code), Toast.LENGTH_SHORT).show()
    }

    fun createPostCall(url: String, data: Any? = null): Call {
        val request = Request.Builder().url(url).post(
            mapper.writeValueAsString(data).toRequestBody(mediaType)).build()
        printTag(request.toString())
        return client.newCall(request)
    }

    fun createGetCall(url: String, data: Any? = null): Call {
        val request = if (data != null) {
            Request.Builder().url("$url/$data").get().build()
        } else {
            Request.Builder().url(url).get().build()
        }
        printTag(request.toString())
        return client.newCall(request)
    }

    fun createDeleteCall(url: String, name: String? = null, data: Any? = null): Call {
        val request = if (name != null) {
            val url0 = "${url}?${name}=${data}"
            Request.Builder().url(url0).delete().build()
        } else {
            Request.Builder().url(url).delete().build()
        }
        printTag(request.toString())
        return client.newCall(request)
    }

    fun createPutCall(url: String, data: Any? = null): Call {
        val request = Request.Builder().url(url).put(
            mapper.writeValueAsString(data).toRequestBody(mediaType)).build()
        printTag(request.toString())
        return client.newCall(request)
    }


    fun printTag(message: String) {
        Log.i("TAG", message)
    }
}