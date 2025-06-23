package com.base.library.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment

inline fun <reified T : Any> Context.startActivityExt(option: Bundle? = null, finishSelf: Boolean = false) {
    val intent = Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    if (option == null) {
        startActivity(intent)
    } else {
        startActivity(intent.apply { putExtras(option) })
    }
    if (finishSelf) {
        (this as Activity).finish()
    }
}


inline fun <reified T : Any> Fragment.startActivityExt(option: Bundle? = null, finishSelf: Boolean = false) {
    val intent = Intent(requireContext(), T::class.java)
    if (option == null)
        startActivity(intent)
    else
        startActivity(intent.apply { putExtras(option) })

    if (finishSelf) {
        activity?.finish()
    }
}

fun Activity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Activity.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}