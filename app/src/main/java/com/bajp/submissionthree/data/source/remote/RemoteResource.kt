package com.bajp.submissionthree.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissionthree.data.source.remote.response.MovieResponse
import com.bajp.submissionthree.data.source.remote.vo.ApiResponse
import com.bajp.submissionthree.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException
import java.util.concurrent.Executors
import javax.inject.Inject

class RemoteResource @Inject constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteResource? = null
        fun getInstance(apiConfig: ApiService): RemoteResource =
            instance ?: synchronized(this) {
                RemoteResource(apiConfig).apply { instance = this }
            }

        private const val TAG = "RemoteResource"
    }

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        scope.launch {
            try {
                val response = apiService.getMovie().await()
                _response.postValue(ApiResponse.success(response.results as List<MovieResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                _response.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

//        val client = apiConfig.getApiService().getMovie()
//        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
//            override fun onResponse(
//                call: Call<BaseResponse<List<MovieResponse>>>,
//                response: Response<BaseResponse<List<MovieResponse>>>
//            ) {
//                val listMovie = response.body()?.results
//                if (response.isSuccessful) {
//                    val data = ApiResponse.success(listMovie as List<MovieResponse>)
//                    _response.postValue(data)
//                }
//            }
//
//            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
//                _response.postValue(ApiResponse.error(t.message ?: "Error", emptyList()))
//            }
//        })

        EspressoIdlingResource.decrement()
        return _response

    }

    fun getDataTv(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<MovieResponse>>>()


        scope.launch {
            try {
                val response = apiService.getTvShow().await()
                _response.postValue(ApiResponse.success(response.results as List<MovieResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                _response.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

//        val client = apiConfig.getApiService().getTvShow()
//        client.enqueue(object : Callback<BaseResponse<List<MovieResponse>>> {
//            override fun onResponse(
//                call: Call<BaseResponse<List<MovieResponse>>>,
//                response: Response<BaseResponse<List<MovieResponse>>>
//            ) {
//                if (response.isSuccessful) {
//                    val listTvShow = response.body()?.results ?: emptyList()
//                    _response.postValue(ApiResponse.success(listTvShow))
//                } else {
//                    _response.value = ApiResponse.error(response.code().toString(), emptyList())
//                }
//            }
//
//            override fun onFailure(call: Call<BaseResponse<List<MovieResponse>>>, t: Throwable) {
//                _response.postValue(ApiResponse.error(t.message ?: "Failed", emptyList()))
//            }
//        })
        EspressoIdlingResource.decrement()
        return _response
    }
}
