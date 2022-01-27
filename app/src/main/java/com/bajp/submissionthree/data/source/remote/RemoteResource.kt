package com.bajp.submissionthree.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissionthree.data.source.remote.response.CatalogResponse
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

    fun getMovies(): LiveData<ApiResponse<List<CatalogResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<CatalogResponse>>>()

        scope.launch {
            try {
                val response = apiService.getMovie().await()
                _response.postValue(ApiResponse.success(response.results as List<CatalogResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                _response.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

        EspressoIdlingResource.decrement()
        return _response

    }

    fun getDataTv(): LiveData<ApiResponse<List<CatalogResponse>>> {
        EspressoIdlingResource.increment()
        val _response = MutableLiveData<ApiResponse<List<CatalogResponse>>>()


        scope.launch {
            try {
                val response = apiService.getTvShow().await()
                _response.postValue(ApiResponse.success(response.results as List<CatalogResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                _response.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

        EspressoIdlingResource.decrement()
        return _response
    }
}
