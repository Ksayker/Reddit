package com.ksayker.reddit.ui.core

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ksayker.reddit.R
import com.ksayker.reddit.utils.isInternetAvailable
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

    protected fun checkNetworkConnection(enableAction: () -> Unit) {
        if (isInternetAvailable()) {
            enableAction.invoke()
        } else {
            Toast.makeText(context, R.string.message_checkNetwork, Toast.LENGTH_SHORT).show()
        }
    }

    fun <T : Any, L : LiveData<T>> observe(liveData: L, body: (T) -> Unit) =
        liveData.observe(this, Observer(body))
}