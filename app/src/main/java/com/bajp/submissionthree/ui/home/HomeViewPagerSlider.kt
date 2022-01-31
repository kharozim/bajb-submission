package com.bajp.submissionthree.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ItemsSliderBinding
import com.bajp.submissionthree.utils.IMAGE_URL
import com.bumptech.glide.Glide

class HomeViewPagerSlider(
    private val context: Context,
    private val items: List<CatalogEntity>
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
        fun bindData(item: CatalogEntity, position: Int) {
            Glide.with(binding.root.context).load(IMAGE_URL+item.imageSlider).into(binding.ivSlider)
            binding.tvTitle.text = item.name
            binding.root.setOnClickListener {
                itemClick?.onItemClick(item, position)
            }
        }
    }
}