package com.ksayker.reddit.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ksayker.reddit.R
import com.ksayker.reddit.ui.screen.image.ImageFragment
import com.ksayker.reddit.ui.screen.postlist.PostListFragment

class MainActivity : AppCompatActivity(), NavigationManager {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = supportFragmentManager.findFragmentByTag(PostListFragment.TAG)

            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fl_mainActivity_fragmentHolder,
                    fragment ?: PostListFragment.newInstance()
                )
                .commit()
        }
    }

    override fun openImageUrl(url: String) {
        val fragment = supportFragmentManager.findFragmentByTag(ImageFragment.TAG)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_mainActivity_fragmentHolder,
                fragment ?: ImageFragment.newInstance(url)
            )
            .addToBackStack(null)
            .commit()
    }

    //todo Yura: this
    override fun openPost() {

    }
}
