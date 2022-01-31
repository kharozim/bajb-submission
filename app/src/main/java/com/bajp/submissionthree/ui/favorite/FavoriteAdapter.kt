package com.bajp.submissionthree.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ItemFavoriteBinding
import com.bajp.submissionthree.databinding.ItemHeaderFavoriteBinding
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.utils.FormatUtil
import com.bajp.submissionthree.utils.IMAGE_URL
import com.bumptech.glide.Glide

sealed class DataItemType {
    data class Header(val title: String) : DataItemType()
    data class Content(val data: CatalogEntity) : DataItemType()
}

class FavoriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<DataItemType>()
    fun setData(data: List<CatalogEntity>) {
        items.clear()
        val dataMovie = data.filter { it.isMovie }
        val dataTv = data.filter { !it.isMovie }
        if (dataMovie.isNotEmpty()) {
            items.add(DataItemType.Header("Movie"))
            dataMovie.forEach {
                items.add(DataItemType.Content(it))
            }
            doAddTv(dataTv)
        } else {
            doAddTv(dataTv)
        }
        notifyDataSetChanged()
    }

    private fun doAddTv(dataTv: List<CatalogEntity>) {
        if (dataTv.isNotEmpty()) {
            items.add(DataItemType.Header("TV Show"))
            dataTv.forEach {
                items.add(DataItemType.Content(it))
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

    private var clickItem: ItemClick? = null
    fun onClickItem(listener: ItemClick) {
        clickItem = listener
    }

    inner class MyViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: DataItemType, position: Int) {
            binding.run {
                item as DataItemType.Content
                Glide.with(root.context).load(IMAGE_URL + item.data.imageSlider).into(ivImage)
                tvTitle.text = item.data.name
                tvSubtitle.text = FormatUtil.formatDate(item.data.releaseDate)
                root.setOnClickListener {
                    clickItem?.onItemClick(item.data, position)
                }
            }
        }
    }

    inner class MyHeader(private val binding: ItemHeaderFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: DataItemType) {
            item as DataItemType.Header
            binding.tvHeader.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CONTENT) {
            MyViewHolder(
                ItemFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            MyHeader(
                ItemHeaderFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }


    override fun getItemCount(): Int = items.size

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            holder as MyHeader
            val item = items[position] as DataItemType.Header
            holder.bindData(item)
        } else {
            holder as MyViewHolder
            val item = (items[position] as DataItemType.Content)
            holder.bindData(item, position)

        }

    }

}
