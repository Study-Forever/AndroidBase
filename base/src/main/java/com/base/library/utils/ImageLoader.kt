package com.base.library.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun loadWithCallback(
        url: String,
        imageView: ImageView,
        successCallback: VoidCallBack? = null,
        failureCallback: VoidCallBack? = null
    ) {
        val finalUrl =
            if (!url.startsWith("http://") && !url.startsWith("https://") && url.startsWith("//")) {
                url.replace("//", "http://")
            } else {
                url
            }
        Glide.with(imageView.context)
            .load(finalUrl)
            .listener(object : RequestListener<Drawable> {

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    successCallback?.invoke()
                    // 返回 false 表示让 Glide 继续处理失败逻辑（例如显示占位图）
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    failureCallback?.invoke()
                    // 返回 false 表示让 Glide 继续处理失败逻辑（例如显示占位图）
                    return false
                }
            })
            .into(imageView)
    }

    fun simpleLoad(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }

    fun loadRoundedImage(imageView: ImageView, url: String, corners: Int) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(corners)))
            .into(imageView)
    }

    fun loadCircleImage(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }
}