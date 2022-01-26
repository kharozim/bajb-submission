package com.bajp.submissionthree.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.local.entities.ContentItemEntity
import com.bajp.submissionthree.vo.Resource

class HomeViewModel(private val repository: IRepository) : ViewModel() {

    fun getListMovie(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return repository.getDataMovie()
    }

    fun getListTV(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return repository.getDataTv()
    }

}