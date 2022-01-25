package com.bajp.submissiontwo.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.ItemFavoriteBinding
import com.bajp.submissiontwo.databinding.ItemHeaderFavoriteBinding

sealed class DataItemType {

    data class Header(val title: String) : DataItemType()

    data class Content(val data: ContentItemEntity) : DataItemType()
}

class FavoriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val headers = ArrayList<String>()
    private val items = ArrayList<DataItemType>()
    fun setData(data: List<ContentItemEntity>) {
        data.forEach {
            if (it.isMovie) {
            } else {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is DataItemType.Header) {
            TYPE_HEADER
        } else {
            TYPE_CONTENT
        }
    }

    inner class MyViewHolder(binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    inner class Header(binding: ItemHeaderFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

    }


    override fun getItemCount(): Int {
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}
