package com.base.library.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle


object ActivityManager : Application.ActivityLifecycleCallbacks {

    // 用于保存当前存在的 Activity
    private val activityList = mutableListOf<Activity>()

    // 获取当前活动的 Activity 列表
    fun getCurrentActivities(): List<Activity> = activityList.toList()

    // 清空所有 Activity
    fun clearAllActivities() {
        for (activity in activityList) {
            activity.finish()
        }
        activityList.clear()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
    }

    // 其余方法根据需要实现，这里留空
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
}