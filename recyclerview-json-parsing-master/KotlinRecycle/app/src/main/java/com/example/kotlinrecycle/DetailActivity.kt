package com.example.kotlinrecycle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity: AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }
    fun init()
    {
        loadJSON()
    }
    fun loadJSON() {
        getService().getClickedPost(intent.getIntExtra("abc", 0)).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val post = response.body()

                tv.append(post!!.id.toString() + "\n")
                tv.append(post.userId.toString() + "\n")
                tv.append(post.title!! + "\n")
                tv.append(post.body!! + "\n")
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("CHANGA!", t.message)
                t.printStackTrace()
            }
        })
    }

    fun createOkHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okHttpBuilder.build()
    }

    fun getService(): JSONPlaceHolderApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JSONPlaceHolderApi::class.java)
    }
}


