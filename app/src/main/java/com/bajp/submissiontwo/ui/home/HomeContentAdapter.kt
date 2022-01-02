package com.bajp.submissiontwo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissiontwo.data.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.ItemsHomeContentBinding
import com.bumptech.glide.Glide

class HomeContentAdapter : RecyclerView.Adapter<HomeContentAdapter.MyViewHolder>() {

    private var onItemClick: ItemClick? = null
    fun clickItem(listener: ItemClick) {
        onItemClick = listener
    }

    private val items = ArrayList<ContentItemEntity>()
    fun setItems(items: List<ContentItemEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
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
        val item = items[position]
        holder.bindData(item, position)
    }

    override fun getItemCount(): Int = items.size

    inner class MyViewHolder(private val binding: ItemsHomeContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ContentItemEntity, position: Int) {
            binding.run {
                Glide.with(root.context).load(item.imagePoster).into(ivImage)
                root.setOnClickListener {
                    onItemClick?.onItemClick(item, position)
                }
            }
        }
    }
}