package com.bajp.submissiontwo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bajp.submissiontwo.data.source.IRepository
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.vo.Resource

class HomeViewModel(private val repository: IRepository) : ViewModel() {

    fun getListMovie(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return repository.getDataMovie()
    }

    fun getListTV(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return repository.getDataTv()
    }

}