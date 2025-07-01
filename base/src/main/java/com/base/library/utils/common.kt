package com.base.library.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun showKeyboard(view: View) {
    view.requestFocus()
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
