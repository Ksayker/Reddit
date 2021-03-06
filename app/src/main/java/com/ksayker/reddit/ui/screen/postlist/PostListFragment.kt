package com.ksayker.reddit.ui.screen.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksayker.reddit.R
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.ui.adapter.EmptyItemAdapter
import com.ksayker.reddit.ui.adapter.LoadingAdapter
import com.ksayker.reddit.ui.adapter.PostAdapter
import com.ksayker.reddit.ui.core.BaseFragment
import com.ksayker.reddit.ui.core.NavigationManager
import com.ksayker.reddit.utils.listener.PostClickListener
import com.ksayker.reddit.utils.listener.UrlClickListener
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment: BaseFragment(), UrlClickListener, LoadingAdapter.LoadingItemListener,
    PostClickListener {
    private val viewModel: PostListViewModel by viewModel()

    override val layoutResId = R.layout.fragment_post_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val postAdapter = PostAdapter(this, this)

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

        requestStoragePermissions()
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
        checkNetworkConnection {
            (activity as? NavigationManager)?.openImageUrl(url)
        }
    }

    override fun onPostClicked(post: Post) {
        checkNetworkConnection {
            (activity as? NavigationManager)?.openPost(post)
        }
    }

    override fun onLoadingItemDisplayed() {
        checkNetworkConnection {
            viewModel.loadNextPage()
        }
    }

    companion object {

        const val TAG = "PostListFragment"
        fun newInstance() = PostListFragment().apply {
            arguments = Bundle()
        }

    }
}