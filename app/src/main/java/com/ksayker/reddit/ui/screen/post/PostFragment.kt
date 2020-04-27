package com.ksayker.reddit.ui.screen.post

import android.os.Bundle
import android.view.View
import com.ksayker.reddit.R
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.ui.core.BaseFragment
import com.ksayker.reddit.ui.core.NavigationManager
import com.ksayker.reddit.utils.getPostTimeFormat
import com.ksayker.reddit.utils.loadImage
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment: BaseFragment() {
    override val layoutResId = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val post = arguments?.getParcelable<Post>(ARG_POST)
        val context = context
        if (post != null && context != null){
            view.tv_post_title.text = post.title
            view.tv_post_commentsCount.text =
                context.getString(R.string.text_postItem_count, post.commentsCount)
            view.tv_post_timestamp.text = getPostTimeFormat(context, post.date)
            view.tv_post_author.text = context.getString(R.string.text_postItem_author, post.author)

            loadImage(view.iv_post_image, post.image)

            view.iv_post_image.setOnClickListener {
                checkNetworkConnection {
                    (activity as? NavigationManager)?.openImageUrl(post.image)
                }
            }
        }
    }

    companion object {
        private const val ARG_POST = "ARG_POST"

        const val TAG = "PostFragment"

        fun newInstance(post: Post) = PostFragment().apply {
            val arg = Bundle()
            arg.putParcelable(ARG_POST, post)

            arguments = arg
        }
    }
}