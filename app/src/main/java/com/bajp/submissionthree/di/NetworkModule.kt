package com.bajp.submissionthree.di

import android.util.Log
import com.bajp.submissionthree.data.source.remote.ApiService
import com.bajp.submissionthree.utils.BASE_URL_API
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {

        @Singleton
        @Provides
        fun provideLogInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Singleton
        @Provides
        fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .apply {
                    connectTimeout(30, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                }
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor { chain ->
                    val origin = chain.request()
                    val request =
                        origin.newBuilder()
                            .header("Authorization", "Bearer $token")
                            .method(origin.method, origin.body)
                            .build()
                    Log.e("TAG", "getApiService: ${request.headers}")
                    chain.proceed(request)
                }.build()

        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder().apply {
                baseUrl(BASE_URL_API)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }.build()

        @Provides
        fun provideCatalogApi(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)


        private const val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMjY1OWZjZmE3NDNlN2I5ZDExYTY4MDA1Njc1ZDI4NiIsInN1YiI6IjViN2QwY2MyMGUwYTI2M2JjZDAwOGVhZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7Jjpjp3o3D8CyIrpN7I9RpPCLFncwF5u-m94UUlxY8A"
    }

}