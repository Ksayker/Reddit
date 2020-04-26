package com.ksayker.reddit.utils

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions

fun requestStoragePermission(activity: FragmentActivity?, onPermissionGranted: (() -> Unit), onPermissionDeny: (() -> Unit)) {
    activity?.let {
        RxPermissions(activity).request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe {
            if (it) {
                onPermissionGranted.invoke()
            } else {
                RxPermissions(activity).shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ).subscribe {
                    if (!it) {
                        onPermissionGranted.invoke()
                    } else {
                        onPermissionDeny.invoke()
                    }
                }
            }
        }
    }
}