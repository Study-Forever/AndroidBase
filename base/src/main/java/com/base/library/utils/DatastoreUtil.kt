package com.base.library.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Suppress("UNCHECKED_CAST")
object DatastoreUtil {

    private val Context.dataStore by preferencesDataStore(name = "webPrefs")
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger("DatastoreUtilException") { "Caught unhandled exception: $throwable" }
        // 这里可以添加你的异常处理逻辑，如日志记录、错误上报等
    }
    private val scope = CoroutineScope(Dispatchers.IO + exceptionHandler)


    fun <T> write(key: String, value: T, complete: (suspend () -> Unit)? = null) {
        val prefsKey = generateKey(key, value)
        scope.launch {
            kotlin.runCatching {
                Global.cxt.dataStore.edit {
                    when (value) {
                        is Int -> it[prefsKey as Preferences.Key<Int>] = value
                        is Long -> it[prefsKey as Preferences.Key<Long>] = value
                        is Float -> it[prefsKey as Preferences.Key<Float>] = value
                        is Boolean -> it[prefsKey as Preferences.Key<Boolean>] = value
                        is String -> it[prefsKey as Preferences.Key<String>] = value
                        else -> throw IllegalArgumentException("Unsupported param type")
                    }
                }
                complete?.invoke()
            }.onFailure {
                Global.cxt.dataStore.edit {
                    when (value) {
                        is Int -> it[prefsKey as Preferences.Key<Int>] = value
                        is Long -> it[prefsKey as Preferences.Key<Long>] = value
                        is Float -> it[prefsKey as Preferences.Key<Float>] = value
                        is Boolean -> it[prefsKey as Preferences.Key<Boolean>] = value
                        is String -> it[prefsKey as Preferences.Key<String>] = value
                        else -> throw IllegalArgumentException("Unsupported param type")
                    }
                }
            }
        }
    }

    fun <T> read(key: String, default: T): T {
        return runBlocking {
            val deferred = async(Dispatchers.IO) { getValue(key, default) }
            deferred.await()
        }
    }

    private suspend fun <T> getValue(key: String, default: T): T {
        val prefsKey = generateKey(key, default)
        return Global.cxt.dataStore.data.map {
            when (default) {
                is Int -> it[prefsKey as Preferences.Key<Int>] ?: default
                is Long -> it[prefsKey as Preferences.Key<Long>] ?: default
                is Float -> it[prefsKey as Preferences.Key<Float>] ?: default
                is Boolean -> it[prefsKey as Preferences.Key<Boolean>] ?: default
                is String -> it[prefsKey as Preferences.Key<String>] ?: default
                else -> throw IllegalArgumentException("Unsupported param type")
            } as T
        }.first()
    }

    private fun <T> generateKey(key: String, value: T): Preferences.Key<T> {
        val prefsKey = when (value) {
            is Int -> {
                intPreferencesKey(key)
            }

            is Long -> {
                longPreferencesKey(key)
            }

            is Float -> {
                floatPreferencesKey(key)
            }

            is Boolean -> {
                booleanPreferencesKey(key)
            }

            is String -> {
                stringPreferencesKey(key)
            }

            else -> {
                throw IllegalArgumentException("Unsupported param type")
            }

        }
        return prefsKey as Preferences.Key<T>
    }

}