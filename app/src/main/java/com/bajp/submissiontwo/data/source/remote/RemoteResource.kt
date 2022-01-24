package com.bajp.submissiontwo.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissiontwo.data.source.remote.response.BaseResponse
import com.bajp.submissiontwo.data.source.remote.response.MovieResponse
import com.bajp.submissiontwo.data.source.remote.vo.ApiResponse
import com.bajp.submissiontwo.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteResource private constructor(private val apiConfig: ApiConfig) {

    companion object {
        @Volatile
        private var instance: RemoteResource? = null
        fun getInstance(apiConfig: ApiConfig): RemoteResource =
            instance ?: synchronized(this) {
                RemoteResource(apiConfig).apply { instance = this }
            }

        private const val TAG = "RemoteResource"
    }

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val client = apiConfig.getApiService().getMovie()
        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<MovieResponse>>>,
                response: Response<BaseResponse<List<MovieResponse>>>
            ) {
                val listMovie = response.body()?.results
                if (response.isSuccessful) {
                    val data = ApiResponse.success(listMovie as List<MovieResponse>)
                    _response.postValue(data)
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
                _response.postValue(ApiResponse.error(t.message ?: "Error", emptyList()))
            }
        })

        EspressoIdlingResource.decrement()
        return _response

    }

    fun getDataTv(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val client = apiConfig.getApiService().getTvShow()
        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<MovieResponse>>>,
                response: Response<BaseResponse<List<MovieResponse>>>
            ) {
                if (response.isSuccessful) {
                    val listTvShow = response.body()?.results ?: emptyList()
                    _response.postValue(ApiResponse.success(listTvShow))
                } else {
                    _response.value = ApiResponse.error(response.code().toString(), emptyList())
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
                _response.postValue(ApiResponse.error(t.message ?: "Failed", emptyList()))
            }
        })
        EspressoIdlingResource.decrement()
        return _response
    }

    fun getDetailMovie(id: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<MovieResponse>>()
        val client = apiConfig.getApiService().getDetailMovie(id)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movie = response.body() ?: MovieResponse()
                    val data = ApiResponse.success(movie)
                    _response.postValue(data)
                } else {
                    val data = ApiResponse.error(response.code().toString(), MovieResponse())
                    _response.postValue(data)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _response.postValue(ApiResponse.error(t.localizedMessage, MovieResponse()))
            }
        })
        EspressoIdlingResource.decrement()
        return _response
    }

    fun getDetailTv(id: Int) : LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<MovieResponse>>()
        val client = apiConfig.getApiService().getDetailTvShow(id)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val tvShow = response.body() ?: MovieResponse()
                    _response.postValue(ApiResponse.success(tvShow))
                } else {
                    _response.postValue(ApiResponse.error(response.message(), MovieResponse()))
                    Log.e(TAG, "onNotSuccessfull: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _response.postValue(ApiResponse.error(t.localizedMessage, MovieResponse()))
            }
        })
        EspressoIdlingResource.decrement()
        return _response
    }
}
