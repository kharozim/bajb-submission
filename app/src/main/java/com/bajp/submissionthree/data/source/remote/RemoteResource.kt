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
    
    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    fun getMovies(): LiveData<ApiResponse<List<CatalogResponse>>> {
        EspressoIdlingResource.increment()
        val data = MutableLiveData<ApiResponse<List<CatalogResponse>>>()

        scope.launch {
            try {
                val response = apiService.getMovie().await()
                data.postValue(ApiResponse.success(response.results as List<CatalogResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                data.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

        EspressoIdlingResource.decrement()
        return data

    }

    fun getDataTv(): LiveData<ApiResponse<List<CatalogResponse>>> {
        EspressoIdlingResource.increment()
        val data = MutableLiveData<ApiResponse<List<CatalogResponse>>>()


        scope.launch {
            try {
                val response = apiService.getTvShow().await()
                data.postValue(ApiResponse.success(response.results as List<CatalogResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                data.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }

        EspressoIdlingResource.decrement()
        return data
    }
}
