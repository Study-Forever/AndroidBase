package com.base.library.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

/**
 * 构建多样式文本的方法
 * @param parts 一个 List，每个元素表示一段文本及其对应样式的设置
 * @return 带样式的 SpannableStringBuilder
 */
fun buildStyledText(parts: List<StyledTextPart>): SpannableStringBuilder {
    val spannableBuilder = SpannableStringBuilder()

    parts.forEach { part ->
        val start = spannableBuilder.length
        spannableBuilder.append(part.text)

        // 设置文本颜色
        part.color?.let { color ->
            spannableBuilder.setSpan(
                ForegroundColorSpan(Global.cxt.getColor(color)),
                start,
                spannableBuilder.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // 设置字体大小
        part.size?.let { size ->
            spannableBuilder.setSpan(
                AbsoluteSizeSpan(size, true), // `true` 表示单位为 sp
                start,
                spannableBuilder.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // 设置字体样式
        part.style?.let { style ->
            spannableBuilder.setSpan(
                StyleSpan(style),
                start,
                spannableBuilder.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    return spannableBuilder
}

/**
 * 样式数据类
 * @param text 文本内容
 * @param color 文本颜色 (可选)
 * @param size 文本大小，单位为 sp (可选)
 * @param style 文本样式，例如 Typeface.BOLD 或 Typeface.ITALIC (可选)
 * 如果想添加下划线，背景色等都可以在这个class中增加
 */
data class StyledTextPart(
    val text: String,
    val color: Int? = null,
    val size: Int? = null,
    val style: Int? = null
)
