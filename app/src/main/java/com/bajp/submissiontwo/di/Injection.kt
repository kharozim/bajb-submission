package com.bajp.submissiontwo.di

import com.bajp.submissiontwo.data.source.Repository
import com.bajp.submissiontwo.data.source.remote.ApiConfig
import com.bajp.submissiontwo.data.source.remote.RemoteResource

object Injection {
    fun provideRepository(): Repository {
        val remoteSource = RemoteResource.getInstance(ApiConfig)
        return Repository.getInstance(remoteSource)
    }
}