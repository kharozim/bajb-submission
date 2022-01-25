package com.bajp.submissiontwo.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissiontwo.data.source.IRepository
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

class FavoriteViewModel(private val repository: IRepository) : ViewModel() {

    fun getFavorite(): LiveData<List<ContentItemEntity>> =
        repository.getFavorite()
}