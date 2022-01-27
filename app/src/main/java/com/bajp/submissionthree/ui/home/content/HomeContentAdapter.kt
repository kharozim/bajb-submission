package com.bajp.submissionthree.ui.home.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ItemsHomeContentBinding
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.utils.IMAGE_URL
import com.bumptech.glide.Glide

class HomeContentAdapter :
    PagedListAdapter<CatalogEntity, HomeContentAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClick: ItemClick? = null
    fun clickItem(listener: ItemClick) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemsHomeContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bindData(item, position)
    }

    inner class MyViewHolder(private val binding: ItemsHomeContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: CatalogEntity, position: Int) {
            binding.run {
                Glide.with(root.context).load(IMAGE_URL+item.imagePoster).into(ivImage)
                root.setOnClickListener {
                    onItemClick?.onItemClick(item, position)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CatalogEntity>() {
            override fun areItemsTheSame(
                oldItem: CatalogEntity,
                newItem: CatalogEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CatalogEntity,
                newItem: CatalogEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}