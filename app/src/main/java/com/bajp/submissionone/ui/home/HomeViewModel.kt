package com.bajp.submissionone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionone.data.entities.ContentEntity
import com.bajp.submissionone.data.repository.IRepository

class HomeViewModel(private val repository: IRepository) : ViewModel() {

    private val _listMovie = MutableLiveData<ContentEntity>()
    val listMovie: LiveData<ContentEntity>
        get() = _listMovie

    fun getListMovie() {
        val data = repository.getDataMovie()
        _listMovie.postValue(data)
    }

    private val _listTV = MutableLiveData<ContentEntity>()
    val listTV: LiveData<ContentEntity>
        get() = _listTV

    fun getListTV() {
        val data = repository.getDataTv()
        _listTV.postValue(data)
    }

}