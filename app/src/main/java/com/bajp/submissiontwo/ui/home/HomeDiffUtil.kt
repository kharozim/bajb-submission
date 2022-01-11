package com.bajp.submissiontwo.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

class HomeDiffUtil(
    private val oldItems: List<ContentItemEntity>,
    private val newItems: List<ContentItemEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (id, name) = oldItems[oldItemPosition]
        val (id1, name1) = newItems[newItemPosition]
        return id == id1 && name == name1
    }

}