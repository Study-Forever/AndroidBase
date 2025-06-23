package com.base.library.utils


import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object PermissionUtil {

    // 定义回调接口
    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    // 在 Activity 中设置权限请求的启动器
    fun setupPermissionRequest(activity: AppCompatActivity, callback: PermissionCallback): ActivityResultLauncher<String> {
        return activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                callback.onPermissionGranted()
            } else {
                callback.onPermissionDenied()
            }
        }
    }

    // 在 Fragment 中设置权限请求的启动器
    fun setupPermissionRequest(fragment: Fragment, callback: PermissionCallback): ActivityResultLauncher<String> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                callback.onPermissionGranted()
            } else {
                callback.onPermissionDenied()
            }
        }
    }

    // 检查权限是否已经被授予
    fun Activity.hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    // 检查权限是否已经被授予 - 片段版本
    fun Fragment.hasPermission(permission: String): Boolean {
        return requireActivity().hasPermission(permission)
    }
}