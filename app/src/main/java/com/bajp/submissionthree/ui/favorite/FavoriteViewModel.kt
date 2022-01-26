package com.bajp.submissionthree.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.local.entities.ContentItemEntity

class FavoriteViewModel(private val repository: IRepository) : ViewModel() {

    fun getFavorite(): LiveData<List<ContentItemEntity>> =
        repository.getFavorite()
}