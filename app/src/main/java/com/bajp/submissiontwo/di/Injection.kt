package com.bajp.submissiontwo.di

import android.content.Context
import com.bajp.submissiontwo.data.source.Repository
import com.bajp.submissiontwo.data.source.local.LocalDataSource
import com.bajp.submissiontwo.data.source.local.room.LocalDatabase
import com.bajp.submissiontwo.data.source.remote.ApiConfig
import com.bajp.submissiontwo.data.source.remote.RemoteResource
import com.bajp.submissiontwo.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = LocalDatabase.getInstance(context)

        val localResource = LocalDataSource.getInstance(database.favDao())
        val remoteSource = RemoteResource.getInstance(ApiConfig)
        val appExecutor = AppExecutors()
        return Repository.getInstance(remoteSource, localResource, appExecutor)
    }
}