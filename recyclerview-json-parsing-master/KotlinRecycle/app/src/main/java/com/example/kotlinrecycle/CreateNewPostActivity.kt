package com.example.kotlinrecycle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_new_post.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateNewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)

        btnSave.setOnClickListener{
            val post = Post(
                id = 101,
                title = title_edit.text.toString(),
                body = body_edit.toString(),
                userId = userId_edit.text.toString().toInt()
            )

            getService().addNewPost(post).enqueue(object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Toast.makeText(this@CreateNewPostActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    Toast.makeText(this@CreateNewPostActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                }

            })

            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okHttpBuilder.build()
    }


    private fun getService(): JSONPlaceHolderApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JSONPlaceHolderApi::class.java)
    }
}
