package com.ksayker.reddit.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class EmptyItemAdapter<T : RecyclerView.Adapter<ViewHolder>>(
    private val adapter: T,
    private val emptyItemCreator: EmptyItemCreator
) : RecyclerView.Adapter<ViewHolder>() {
    private val dataObserver: AdapterDataObserver

    var showEmptyItem = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        dataObserver = object : AdapterDataObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                if (positionStart == 0 && adapter.itemCount == itemCount) {
                    notifyItemRangeChanged(positionStart, 1)
                    if (itemCount > 1) {
                        notifyItemRangeInserted(positionStart + 1, itemCount - 1)
                    }
                } else {
                    notifyItemRangeInserted(positionStart, itemCount)
                }
            }

            override fun onItemRangeRemoved(
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }
        }
        adapter.registerAdapterDataObserver(dataObserver)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        if (!hasObservers()) { //use setHasStableIds before set to RecyclerView
            adapter.unregisterAdapterDataObserver(dataObserver)
            adapter.setHasStableIds(hasStableIds)
            adapter.registerAdapterDataObserver(dataObserver)
        }
    }

    override fun getItemId(position: Int): Long {
        return adapter.getItemId(position)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        adapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return adapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        adapter.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView)
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

    companion object {
        private const val EMPTY_ITEM = -1000000
    }
}