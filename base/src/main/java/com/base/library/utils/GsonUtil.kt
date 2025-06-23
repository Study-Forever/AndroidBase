package com.base.library.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object GsonUtil {

    fun <T> list2Json(list: List<T>): String {
        val type = object : TypeToken<List<T>>() {}.type
        return Gson().toJson(list, type)
    }

    inline fun <reified T> json2List(json: String): List<T> {
        if (TextUtils.isEmpty(json)) {
            return mutableListOf()
        }
        val type = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun toJson(obj: Any?): String? {
        return if (obj == null) null else Gson().toJson(obj)
    }

    inline fun <reified T> fromJson(json: String?): T? {
        val type = object : TypeToken<T>() {}.type
        return if (json.isNullOrEmpty()) null else Gson().fromJson(json, type)
    }
}