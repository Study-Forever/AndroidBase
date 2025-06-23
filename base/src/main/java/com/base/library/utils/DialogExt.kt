package com.base.library.utils

import android.app.Dialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

const val TAG = "Dialog_TAG"

fun Dialog.safeShow() {
    try {
        this.show()
    } catch (e: Exception) {
        logger(TAG, ){"Dialog show exception:${e.message}"}
    }
}

fun Dialog.safeDismiss() {
    try {
        this.dismiss()
    } catch (e: Exception) {
        logger(TAG, ){"Dialog dismiss exception:${e.message}"}
    }
}

fun DialogFragment.safeShow(manager: FragmentManager, tag: String?) {
    try {
        this.show(manager, tag)
    } catch (e: Exception) {
        logger(TAG, ){"DialogFragment show exception:${e.message}"}
    }
}

fun DialogFragment.safeDismiss() {
    try {
        this.dismiss()
    } catch (e: Exception) {
        logger(TAG, ){"DialogFragment dismiss exception:${e.message}"}
    }
}