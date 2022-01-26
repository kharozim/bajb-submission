package com.bajp.submissionthree.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bajp.submissionthree.data.source.remote.vo.ApiResponse
import com.bajp.submissionthree.data.source.remote.vo.StatusResponse
import com.bajp.submissionthree.utils.AppExecutors
import com.bajp.submissionthree.vo.Resource
import kotlinx.coroutines.*
import java.util.concurrent.Executors

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS ->
                    scope.launch {
                        response.body?.let { data ->
                            saveCallResult(data)
                        }
                        Log.d("SCOP 1 : ", response.status.name)

                        withContext(Dispatchers.Main) {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.ERROR -> {
                    Log.d("SCOP 2 : ", response.status.name)
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}