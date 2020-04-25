package com.ksayker.reddit.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ksayker.reddit.R
import com.ksayker.reddit.ui.screen.postlist.PostListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
    }

    private fun initFragment() {
        val fragmentId = R.id.fl_mainActivity_fragmentHolder

        val fragment = supportFragmentManager.findFragmentById(fragmentId)
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentId, fragment ?: PostListFragment.newInstance())
            .commit()
    }
}
