package com.bajp.submissionthree.data.source.remote

import com.bajp.submissionthree.data.source.remote.response.BaseResponse
import com.bajp.submissionthree.data.source.remote.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular")
    fun getMovie(): Call<BaseResponse<List<MovieResponse>>>

    @GET("tv/popular")
    fun getTvShow(): Call<BaseResponse<List<MovieResponse>>>

    @GET("movie/{id}")
    fun getDetailMovie(
        @Path("id") id: Int
    ): Call<MovieResponse>

    @GET("tv/{id}")
    fun getDetailTvShow(
        @Path("id") id: Int
    ): Call<MovieResponse>
}