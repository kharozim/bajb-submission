package com.bajp.submissiontwo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissiontwo.data.entities.ContentEntity
import com.bajp.submissiontwo.data.repository.IRepository

class HomeViewModel(private val repository: IRepository) : ViewModel() {

    fun getListMovie(): LiveData<ContentEntity> {
        return repository.getDataMovie()
    }

    fun getListTV(): LiveData<ContentEntity> {
        return repository.getDataTv()
    }

}