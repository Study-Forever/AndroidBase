package com.base.library.utils

import android.os.SystemClock
import android.view.View
import android.widget.Toast

fun View.clickWithDebounce(debounce: Long = 600L, action: () -> Unit) {
    var lastClickTime = 0L
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < debounce) return@setOnClickListener
        else action.invoke()
        lastClickTime = SystemClock.elapsedRealtime()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun showToast(content: String) {
    Toast.makeText(Global.cxt, content, Toast.LENGTH_SHORT).show()
}

fun showToast(resId: Int) {
    Toast.makeText(Global.cxt, Global.cxt.getString(resId), Toast.LENGTH_SHORT)
        .show()
}

