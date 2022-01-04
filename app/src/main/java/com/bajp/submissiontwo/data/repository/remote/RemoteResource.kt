package com.bajp.submissiontwo.data.repository.remote

import android.util.Log
import com.bajp.submissiontwo.data.repository.remote.response.BaseResponse
import com.bajp.submissiontwo.data.repository.remote.response.MovieResponse
import com.bajp.submissiontwo.data.repository.remote.response.TvShowResponse
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

    fun getDataMovie(callback: CallbackListMovie) {
        EspressoIdlingResource.increment()
        val client = apiConfig.getApiService().getMovie()
        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<MovieResponse>>>,
                response: Response<BaseResponse<List<MovieResponse>>>
            ) {
                if (response.isSuccessful) {
                    val listMovie = response.body()?.results ?: emptyList()
                    callback.onAllMovieReceived(listMovie)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
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
                    callback.onDetailMovieReceived(movie)
                } else {
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
        fun onAllMovieReceived(data: List<MovieResponse>)
    }

    interface CallBackListTvShow {
        fun onAllTvShowReceived(data: List<TvShowResponse>)
    }

    interface CallbackDetailMovie {
        fun onDetailMovieReceived(data: MovieResponse)
    }

    interface CallbackDetailTvShow {
        fun onDetailTvReceived(data: TvShowResponse)
    }
}
