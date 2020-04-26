package com.ksayker.reddit.ui.core

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ksayker.reddit.utils.requestStoragePermission

abstract class BaseFragment : Fragment() {

    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    protected fun requestStoragePermissions(onGranted: () -> Unit = {}) {
        Handler().post {
            requestStoragePermission(
                activity,
                onGranted,
                { activity?.finish() })
        }
    }

    fun <T : Any, L : LiveData<T>> observe(liveData: L, body: (T) -> Unit) =
        liveData.observe(this, Observer(body))
}