package com.bajp.submissiontwo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.ItemsHomeContentBinding
import com.bajp.submissiontwo.utils.IMAGE_URL
import com.bumptech.glide.Glide

class HomeContentAdapter :
    PagedListAdapter<ContentItemEntity, HomeContentAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        fun bindData(item: ContentItemEntity, position: Int) {
            binding.run {
                Glide.with(root.context).load(IMAGE_URL+item.imagePoster).into(ivImage)
                root.setOnClickListener {
                    onItemClick?.onItemClick(item, position)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentItemEntity>() {
            override fun areItemsTheSame(
                oldItem: ContentItemEntity,
                newItem: ContentItemEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ContentItemEntity,
                newItem: ContentItemEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}