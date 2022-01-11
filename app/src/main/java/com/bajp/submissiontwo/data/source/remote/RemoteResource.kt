package com.bajp.submissiontwo.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissiontwo.data.source.remote.response.BaseResponse
import com.bajp.submissiontwo.data.source.remote.response.MovieResponse
import com.bajp.submissiontwo.data.source.remote.response.TvShowResponse
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

    fun getDataMovie(callback: CallbackListMovie) : LiveData<ApiResponse<BaseResponse<List<MovieResponse>>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<BaseResponse<List<MovieResponse>>>>()
        val client = apiConfig.getApiService().getMovie()
        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<MovieResponse>>>,
                response: Response<BaseResponse<List<MovieResponse>>>
            ) {
                if (response.isSuccessful) {
                    val listMovie = response.body()!!
                    val data = ApiResponse.success(listMovie)
                    _response.postValue(data)
//                    callback.onAllMovieReceived(listMovie)
                } else {
                    Log.e(TAG, "onEmpty: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
                val data = ApiResponse.error(t.localizedMessage,)
                _response.postValue(data)
            }
        })

        EspressoIdlingResource.decrement()


    }

    fun getDataTv(callback: CallBackListTvShow) {
        EspressoIdlingResource.increment()
        val client = apiConfig.getApiService().getTvShow()
        client.enqueue(object : Callback<BaseResponse<List<TvShowResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<TvShowResponse>>>,
                response: Response<BaseResponse<List<TvShowResponse>>>
            ) {
                if (response.isSuccessful) {
                    val listTvShow = response.body()?.results ?: emptyList()
                    callback.onAllTvShowReceived(listTvShow)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<TvShowResponse>>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
        EspressoIdlingResource.decrement()

    }

    fun getDetailMovie(id: Int, callback: CallbackDetailMovie) {
        EspressoIdlingResource.increment()
        val client = apiConfig.getApiService().getDetailMovie(id)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movie = response.body() ?: MovieResponse()
                    val data = ApiResponse.success(movie)
                    callback.onDetailMovieReceived(data)
                } else {
                    val data = ApiResponse.error(response.message(), response.body()?: MovieResponse())
                    callback.onDetailMovieReceived(data)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
        EspressoIdlingResource.decrement()
    }

    fun getDetailTv(id: Int, callback: CallbackDetailTvShow) {
        EspressoIdlingResource.increment()
        val client = apiConfig.getApiService().getDetailTvShow(id)
        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                if (response.isSuccessful) {
                    val tvShow = response.body() ?: TvShowResponse()
                    callback.onDetailTvReceived(tvShow)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
        EspressoIdlingResource.decrement()
    }


    interface CallbackListMovie {
        fun onAllMovieReceived(data: ApiResponse<List<MovieResponse>>)
    }

    interface CallBackListTvShow {
        fun onAllTvShowReceived(data: ApiResponse<List<TvShowResponse>>)
    }

    interface CallbackDetailMovie {
        fun onDetailMovieReceived(data: ApiResponse<MovieResponse>)
    }

    interface CallbackDetailTvShow {
        fun onDetailTvReceived(data: ApiResponse<TvShowResponse>)
    }
}
