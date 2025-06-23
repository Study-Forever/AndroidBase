package com.base.library.utils

import android.app.Activity
import android.util.Log
import com.base.library.BuildConfig

inline fun logger(tag: String, throwable: Throwable? = null, block: () -> String) {
    BuildConfig.DEBUG.takeIf { it }?.let {
        Log.e(tag, block(), throwable)
    }
}

fun Activity?.isKeepLive(): Boolean {
    this ?: return false
    return !this.isFinishing && !this.isDestroyed
}

val Int.stringRes: String
    get() = Global.cxt.getString(this)

val Int.colorRes: Int
    get() = Global.cxt.resources.getColor(this)
