package com.ksayker.reddit.ui.screen.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksayker.reddit.R
import com.ksayker.reddit.ui.adapter.EmptyItemAdapter
import com.ksayker.reddit.ui.adapter.LoadingAdapter
import com.ksayker.reddit.ui.adapter.PostAdapter
import com.ksayker.reddit.ui.core.BaseFragment
import com.ksayker.reddit.ui.core.NavigationManager
import com.ksayker.reddit.utils.isInternetAvailable
import com.ksayker.reddit.utils.listener.UrlClickListener
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment: BaseFragment(), UrlClickListener, LoadingAdapter.LoadingItemListener {
    private val viewModel: PostListViewModel by viewModel()

    override val layoutResId = R.layout.fragment_post_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val postAdapter = PostAdapter(this)

        observe(viewModel.ldPostItems) {
            postAdapter.setItems(it)
        }

        rv_postList_list.run {
            layoutManager = LinearLayoutManager(context)

            adapter = EmptyItemAdapter(
                LoadingAdapter(
                    postAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                    getLoadingItemCreator(),
                    this@PostListFragment),
                getEmptyItemCreator()
            ).apply {
                showEmptyItem = true
            }
        }
    }

    private fun getEmptyItemCreator(): EmptyItemAdapter.EmptyItemCreator {
        return object : EmptyItemAdapter.EmptyItemCreator {
            override fun createEmptyItem(parent: ViewGroup): View {
                return LayoutInflater.from(context)
                    .inflate(R.layout.item_post_empty, rv_postList_list, false)
            }
        }
    }

    private fun getLoadingItemCreator(): LoadingAdapter.LoadingItemCreator {
        return object : LoadingAdapter.LoadingItemCreator {
            override fun createLoadingItem(parent: ViewGroup): View {
                return LayoutInflater.from(context)
                    .inflate(R.layout.item_post_loading, rv_postList_list, false)
            }
        }
    }

    override fun onUrlClicked(url: String) {
        if (isInternetAvailable()) {
            (activity as? NavigationManager)?.openImageUrl(url)
        } else {
            Toast.makeText(context, R.string.message_checkNetwork, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoadingItemDisplayed() {
        if (isInternetAvailable()) {
            viewModel.loadNextPage()
        } else {
            Toast.makeText(context, R.string.message_checkNetwork, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        const val TAG = "PostListFragment"
        fun newInstance() = PostListFragment().apply {
            arguments = Bundle()
        }

    }
}