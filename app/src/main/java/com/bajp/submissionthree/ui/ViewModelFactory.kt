package com.bajp.submissionthree.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.di.Injection
import com.bajp.submissionthree.ui.detail.DetailViewModel
import com.bajp.submissionthree.ui.favorite.FavoriteViewModel
import com.bajp.submissionthree.ui.home.HomeViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repo: IRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
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
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->{
                FavoriteViewModel(repo) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}