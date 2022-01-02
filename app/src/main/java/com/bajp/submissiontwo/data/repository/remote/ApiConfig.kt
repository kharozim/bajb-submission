package com.bajp.submissiontwo.data.repository.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val baseUrl = "https://api.themoviedb.org/3/"
    private const val token =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMjY1OWZjZmE3NDNlN2I5ZDExYTY4MDA1Njc1ZDI4NiIsInN1YiI6IjViN2QwY2MyMGUwYTI2M2JjZDAwOGVhZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7Jjpjp3o3D8CyIrpN7I9RpPCLFncwF5u-m94UUlxY8A"

    fun getApiService(): ApiService {
        val logInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor { chain ->
                val origin = chain.request()
                val request =
                origin.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .method(origin.method, origin.body)
                    .build()
                Log.e("TAG", "getApiService: ${request.headers}")
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}