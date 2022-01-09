package com.vaithidroid.appone.how2cook.util

import androidx.recyclerview.widget.DiffUtil
import com.vaithidroid.appone.how2cook.models.Result

class RecipeDiffUtil(
    val oldList: List<Result>,
    val newList: List<Result>
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