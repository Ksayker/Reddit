package com.ksayker.reddit.ui.screen.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksayker.reddit.R
import com.ksayker.reddit.ui.adapter.EmptyItemAdapter
import com.ksayker.reddit.ui.adapter.PostAdapter
import com.ksayker.reddit.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment: BaseFragment() {
    override val layoutResId: Int
        get() = R.layout.fragment_post_list

    private val viewModel: PostListModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val postAdapter = PostAdapter()

        observe(viewModel.ldPostItems) {
            postAdapter.setItems(it)
        }

        rv_postList_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = EmptyItemAdapter(
                    postAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                    object : EmptyItemAdapter.EmptyItemCreator {
                        override fun createEmptyItem(parent: ViewGroup): View {
                            return LayoutInflater.from(context)
                                .inflate(R.layout.item_post_empty, rv_postList_list, false)
                        }
                    }).apply {
                    showEmptyItem = true
                }
        }

        viewModel.initList(savedInstanceState == null)
    }

    companion object {
        fun newInstance() = PostListFragment().apply {
            arguments = Bundle()
        }
    }
}