package com.ksayker.reddit.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class EmptyItemAdapter<T : RecyclerView.Adapter<ViewHolder>>(
    private val adapter: T,
    private val emptyItemCreator: EmptyItemCreator
) : WrapperAdapter(adapter) {

    var showEmptyItem = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == EMPTY_ITEM) {
            object : ViewHolder(emptyItemCreator.createEmptyItem(parent)) {}
        } else {
            adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) != EMPTY_ITEM) {
            adapter.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        var result = adapter.itemCount
        if (result == 0 && showEmptyItem) {
            result = 1
        }
        return result
    }

    override fun getItemViewType(position: Int): Int {
        var result = EMPTY_ITEM
        if (adapter.itemCount != 0) {
            result = adapter.getItemViewType(position)
        }
        return result
    }

    fun getAdapter(): T {
        return adapter
    }

    interface EmptyItemCreator {
        fun createEmptyItem(parent: ViewGroup): View
    }
}