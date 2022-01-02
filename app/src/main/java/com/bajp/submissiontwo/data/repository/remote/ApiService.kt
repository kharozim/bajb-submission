package com.bajp.submissiontwo.data.repository.remote

import com.bajp.submissiontwo.data.repository.remote.response.BaseResponse
import com.bajp.submissiontwo.data.repository.remote.response.MovieResponse
import com.bajp.submissiontwo.data.repository.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular")
    fun getMovie(): Call<BaseResponse<List<MovieResponse>>>

    @GET("tv/popular")
    fun getTvShow(): Call<BaseResponse<List<TvShowResponse>>>

    @GET("movie/{id}")
    fun getDetailMovie(
        @Path("id") id: Int
    ): Call<MovieResponse>

    @GET("tv/{id}")
    fun getDetailTvShow(
        @Path("id") id: Int
    ): Call<TvShowResponse>
}