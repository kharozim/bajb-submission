package com.bajp.submissionthree.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.IRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    fun getDetailMovie(id: Int): LiveData<CatalogEntity> {
        return repository.getDetailMovie(id)
    }

    fun getDetailTv(id: Int): LiveData<CatalogEntity> {
        return repository.getDetailTv(id)
    }

    fun setFav(item: CatalogEntity) {
        repository.setFavorite(item)
    }
}