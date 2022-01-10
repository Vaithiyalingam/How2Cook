package com.vaithidroid.appone.how2cook.util

import androidx.recyclerview.widget.DiffUtil
import com.vaithidroid.appone.how2cook.models.Result

class RecipeDiffUtil<T>(
    val oldList: List<T>,
    val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}