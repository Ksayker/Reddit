package com.ksayker.reddit.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class WrapperAdapter(private val adapter: RecyclerView.Adapter<ViewHolder>) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataObserver: RecyclerView.AdapterDataObserver

    init {
        dataObserver = object : RecyclerView.AdapterDataObserver() {
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

    companion object {
        const val EMPTY_ITEM = 1000000
        const val LOADING_ITEM = 1000001
    }
}