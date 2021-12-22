package com.bajp.submissionone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.data.repository.IRepository

class DetailViewModel(private val repository : IRepository) : ViewModel() {

    private val _detailMovie = MutableLiveData<ContentItemEntity?>()
    val detailMovie: LiveData<ContentItemEntity?>
        get() = _detailMovie

    fun getDetailMovie(id: Int) {
        val data = repository.getDetailMovie(id)
        _detailMovie.postValue(data)
    }

    private val _detailTv = MutableLiveData<ContentItemEntity?>()
    val detailTv: LiveData<ContentItemEntity?>
        get() = _detailTv

    fun getDetailTv(id: Int) {
        val data = repository.getDetailTv(id)
        _detailTv.postValue(data)
    }
}