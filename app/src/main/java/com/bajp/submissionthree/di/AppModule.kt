package com.bajp.submissionthree.di

import android.app.Application
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.Repository
import com.bajp.submissionthree.data.source.local.LocalDataSource
import com.bajp.submissionthree.data.source.local.room.LocalDao
import com.bajp.submissionthree.data.source.local.room.LocalDatabase
import com.bajp.submissionthree.data.source.remote.ApiService
import com.bajp.submissionthree.data.source.remote.RemoteResource
import com.bajp.submissionthree.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        @Singleton
        @Provides
        fun providesLocalDatabase(application: Application): LocalDatabase =
            LocalDatabase.getInstance(application)

        @Singleton
        @Provides
        fun provideCatalogDao(localDatabase: LocalDatabase): LocalDao =
            localDatabase.favDao()

        @Singleton
        @Provides
        fun provideLocalDataSource(localDao: LocalDao): LocalDataSource =
            LocalDataSource(localDao)

        @Singleton
        @Provides
        fun provideRemoteDataSource(apiService: ApiService): RemoteResource =
            RemoteResource(apiService)

        @Singleton
        @Provides
        fun provideCatalogRepository(
            remoteDataSource: RemoteResource,
            localDataSource: LocalDataSource
        ): IRepository =
            Repository(remoteDataSource, localDataSource)

        @Singleton
        @Provides
        fun provideViewModelFactory(repo: IRepository): ViewModelFactory =
            ViewModelFactory(repo)

    }
}