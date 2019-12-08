package com.example.kotlinrecycle

import android.widget.TextView
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JSONPlaceHolderApi {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("/posts/{id}")
    fun getClickedPost(@Path("id") id: Int): Call<Post>
    @POST("posts")
    fun addNewPost(@Body post: Post): Call<Post>

}