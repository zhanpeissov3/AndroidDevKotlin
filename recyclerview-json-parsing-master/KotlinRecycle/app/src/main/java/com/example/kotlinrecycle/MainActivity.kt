@file:Suppress("DEPRECATION")

package com.example.kotlinrecycle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.List
import android.content.Intent as Intent

@Suppress("UNREACHABLE_CODE")
class MainActivity() : AppCompatActivity(), RVAdapter.MyClickListener {
    private var rvAdapter = RVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvAdapter.setListener(this)
        initViews()

        addNewPostBtn.setOnClickListener{
                    val intent = Intent(this, CreateNewPostActivity::class.java)
                    startActivity(intent)
                }
    }
    fun initViews() {
        rv.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = layoutManager
        rv.adapter = rvAdapter
        loadJSON()
        updateData()
    }
    fun loadJSON() {
            getService().getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                rvAdapter.setPost(response.body() as ArrayList<Post>)
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("CHANGA!", t.message)
                t.printStackTrace()
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        updateData()
        Toast.makeText(this, "list updated", Toast.LENGTH_SHORT).show()
    }
    private fun updateData(){
        getService().getPosts().enqueue(object: Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                rvAdapter.setPost(response.body() as ArrayList<Post>)
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

    override fun onClick(item: Post) {
        //Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("abc", item.id)
        startActivity(intent)
    }



}

