package com.bajp.submissiontwo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissiontwo.data.repository.IRepository
import com.bajp.submissiontwo.di.Injection
import com.bajp.submissiontwo.ui.detail.DetailViewModel
import com.bajp.submissiontwo.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val repo: IRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory {
            return instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository()).apply { instance = this }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}