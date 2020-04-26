package com.ksayker.reddit.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class LoadingAdapter<T : RecyclerView.Adapter<ViewHolder>>(
    private val adapter: T,
    private val loadingItemCreator: LoadingItemCreator,
    private val loadingItemListener: LoadingItemListener? = null
) : WrapperAdapter(adapter) {

    var showLoadingItem = true
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LOADING_ITEM) {
            object : ViewHolder(loadingItemCreator.createLoadingItem(parent)) {}
        } else {
            adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == LOADING_ITEM) {
            loadingItemListener?.onLoadingItemDisplayed()
        } else {
            adapter.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        var result = adapter.itemCount
        if (showLoadingItem) {
            result++
        }
        return result
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != adapter.itemCount) {
            adapter.getItemViewType(position)
        } else {
            LOADING_ITEM
        }
    }

    fun getAdapter(): T {
        return adapter
    }

    interface LoadingItemCreator {
        fun createLoadingItem(parent: ViewGroup): View
    }

    interface LoadingItemListener {
        fun onLoadingItemDisplayed()
    }
}