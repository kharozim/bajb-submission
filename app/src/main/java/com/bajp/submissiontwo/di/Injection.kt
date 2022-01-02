package com.bajp.submissiontwo.di

import com.bajp.submissiontwo.data.repository.Repository
import com.bajp.submissiontwo.data.repository.remote.ApiConfig
import com.bajp.submissiontwo.data.repository.remote.RemoteResource

object Injection {
    fun provideRepository(): Repository {
        val remoteSource = RemoteResource.getInstance(ApiConfig)
        return Repository.getInstance(remoteSource)
    }
}