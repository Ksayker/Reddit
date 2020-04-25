package com.ksayker.reddit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksayker.reddit.R
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.utils.extension.gone
import com.ksayker.reddit.utils.extension.visible
import com.ksayker.reddit.utils.getPostTimeFormat
import com.ksayker.reddit.utils.listener.UrlClickListener
import com.ksayker.reddit.utils.loadImage
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(private val urlClickListener: UrlClickListener) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val items = ArrayList<Post>()

    fun setItems(newItems: List<Post>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        private val tvAuthor = itemView.tv_postItem_author
        private val ivImage = itemView.iv_postItem_image
        private val tvCommentCount = itemView.tv_postItem_commentsCount
        private val tvTimestamp = itemView.tv_postItem_timestamp

        fun bind(post: Post) {
            tvAuthor.text = context.getString(R.string.text_postItem_author, post.author)
            tvCommentCount.text = context.getString(R.string.text_postItem_count, post.commentsCount)
            tvTimestamp.text = getPostTimeFormat(context, post.date)

            if (post.image == Post.NO_IMAGE) {
                ivImage.gone()
                ivImage.setOnClickListener(null)
            } else {
                ivImage.visible()
                ivImage.setOnClickListener { urlClickListener.onUrlClicked(post.image) }
                loadImage(ivImage, post.image)
            }
        }
    }
}