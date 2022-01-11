package com.bajp.submissiontwo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.ItemsSliderBinding
import com.bumptech.glide.Glide

class HomeViewPagerSlider(
    private val context: Context,
    private val items: List<ContentItemEntity>
) : RecyclerView.Adapter<HomeViewPagerSlider.MyViewHolder>() {

    private var itemClick: ItemClick? = null
    fun onItemClick(listener: ItemClick) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemsSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: MyViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(context).clear(holder.binding.ivSlider)
    }

    inner class MyViewHolder(val binding: ItemsSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ContentItemEntity, position: Int) {
            Glide.with(binding.root.context).load(item.imageSlider).into(binding.ivSlider)
            binding.tvTitle.text = item.name
            binding.root.setOnClickListener {
                itemClick?.onItemClick(item, position)
            }
        }
    }
}