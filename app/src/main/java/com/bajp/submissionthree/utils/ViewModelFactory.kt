package com.bajp.submissionthree.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.ui.detail.DetailViewModel
import com.bajp.submissionthree.ui.favorite.FavoriteViewModel
import com.bajp.submissionthree.ui.home.HomeViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repo: IRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repo) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repo) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(repo) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}