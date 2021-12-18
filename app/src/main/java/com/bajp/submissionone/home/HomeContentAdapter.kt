package com.bajp.submissionone.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.databinding.ItemsHomeContentBinding
import com.bumptech.glide.Glide

class HomeContentAdapter : RecyclerView.Adapter<HomeContentAdapter.MyViewHolder>() {

    interface HomeContentClick {
        fun onContentClick(data: Any?, position: Int)
    }

    private var onItemClick: HomeContentClick? = null
    fun clickItem(listener: HomeContentClick) {
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
                    onItemClick?.onContentClick(item, position)
                }
            }
        }
    }
}