package com.bajp.submissionone.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionone.data.ContentEntity
import com.bajp.submissionone.data.repository.IRepository
import com.bajp.submissionone.data.repository.Repository
import com.bajp.submissionone.utils.DataUtil

class HomeViewModel : ViewModel() {

    private val _listMovie = MutableLiveData<ContentEntity>()
    val listMovie: LiveData<ContentEntity>
        get() = _listMovie

    fun getListMovie(context: Context) {
        val repository: IRepository = Repository()
        val json = DataUtil.generateDataMovie(context)
        val data = repository.getDataMovie(json ?: "")
        _listMovie.postValue(data)
    }

    private val _listTV = MutableLiveData<ContentEntity>()
    val listTV: LiveData<ContentEntity>
        get() = _listTV

    fun getListTV(context: Context) {
        val repository: IRepository = Repository()
        val json = DataUtil.generateDataTV(context)
        val data = repository.getDataTv(json ?: "")
        _listTV.postValue(data)
    }

}