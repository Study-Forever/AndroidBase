package com.base.library.utils


object StringUtil {

    fun appendText(split: String, appendText: String, oldText: String): String {
        return if (oldText.isEmpty()) {
            appendText
        } else {
            oldText.plus(split).plus(appendText)
        }
    }
}