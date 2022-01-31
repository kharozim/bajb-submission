package com.bajp.submissionthree.data.source.remote

import com.bajp.submissionthree.data.source.remote.response.BaseResponse
import com.bajp.submissionthree.data.source.remote.response.CatalogResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular")
    fun getMovie(): Call<BaseResponse<List<CatalogResponse>>>

    @GET("tv/popular")
    fun getTvShow(): Call<BaseResponse<List<CatalogResponse>>>
}