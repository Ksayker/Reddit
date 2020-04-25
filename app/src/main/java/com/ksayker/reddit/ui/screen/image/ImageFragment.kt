package com.ksayker.reddit.ui.screen.image

import android.os.Bundle
import android.view.View
import com.ksayker.reddit.R
import com.ksayker.reddit.ui.core.BaseFragment
import com.ksayker.reddit.utils.loadImage
import kotlinx.android.synthetic.main.fragment_image.view.*

class ImageFragment: BaseFragment() {
    override val layoutResId = R.layout.fragment_image

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = arguments?.getString(ARG_IMAGE)

        url?.let {
            loadImage(view.ivImage, url)
        }
    }

    companion object {
        private const val ARG_IMAGE = "ARG_IMAGE"

        const val TAG = "ImageFragment"

        fun newInstance(imageUrl: String) = ImageFragment().apply {
            val arg = Bundle()
            arg.putString(ARG_IMAGE, imageUrl)

            arguments = arg
        }
    }
}