package com.bajp.submissionthree.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.vo.Resource
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    fun getListMovie(): LiveData<Resource<PagedList<CatalogEntity>>> {
        return repository.getDataMovie()
    }

    fun getListTV(): LiveData<Resource<PagedList<CatalogEntity>>> {
        return repository.getDataTv()
    }

}